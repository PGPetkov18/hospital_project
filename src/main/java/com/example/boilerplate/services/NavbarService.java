package com.example.boilerplate.services;

import com.example.boilerplate.models.Navbar;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
@AllArgsConstructor
public class NavbarService {


    private Map<String, String> setMapForAdmin() {
        Map<String, String> elements = new LinkedHashMap<>();
        elements.put("/doctorsMenu", "Меню за доктори");
        elements.put("/receptionistsMenu", "Меню за рецепционисти");
        elements.put("/login/logout", "Излез");

        return elements;
    }

    private Map<String, String> setMapForDoctor() {
        Map<String, String> elements = new LinkedHashMap<>();
        elements.put("/patientsMenu/all", "Пациенти");
        elements.put("/visitsMenu", "Посещения");
        elements.put("/dischargeSummaryMenu", "Епикризи");
        elements.put("/medicine", "Лекарства");
        elements.put("/login/logout", "Излез");

        return elements;
    }

    private Map<String, String> setMapForReceptionist() {
        Map<String, String> elements = new LinkedHashMap<>();
        elements.put("/patientsMenu", "Меню за пациенти");
        elements.put("/dischargeSummaryMenu/all", "Епикризи");
        elements.put("/visitsMenu", "Меню за посещения");
        elements.put("/login/logout", "Излез");

        return elements;
    }

    private Map<String, String> setMapForPatient() {
        Map<String, String> elements = new LinkedHashMap<>();
        elements.put("/visitsMenu", "Меню за посещения");
        elements.put("/relativesMenu", "Меню за роднини");
        elements.put("/login/logout", "Излез");

        return elements;
    }

    private Navbar getNavbarByRoleName(String roleName) {

        return switch (roleName) {
            case "admin" -> new Navbar(setMapForAdmin());
            case "doctor" -> new Navbar(setMapForDoctor());
            case "receptionist" -> new Navbar(setMapForReceptionist());
            case "patient" -> new Navbar(setMapForPatient());

            default -> null;
        };

    }


    public Navbar getNavbar(String sessionId, SessionService sessionService) {

        if (sessionId.equals("")) {
            Map<String, String> temp = new TreeMap<>();
            temp.put("/login", "Вписване");
            temp.put("/register", "Регистрация");
            temp.put("/", "Начало");
            return new Navbar(temp);


        } else {
            return getNavbarByRoleName(sessionService.findById(sessionId).getRoleName());
        }
    }

}
