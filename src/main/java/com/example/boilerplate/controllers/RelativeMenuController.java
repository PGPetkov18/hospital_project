package com.example.boilerplate.controllers;

import com.example.boilerplate.exceptions.UserDoesNotHavePermissionException;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Relative;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/relativesMenu")
@AllArgsConstructor
public class RelativeMenuController {

    private RelativeService relativeService;
    private PatientService patientService;
    private SessionService sessionService;
    private CookieService cookieService;
    private RoleService roleService;
    private NavbarService navbarService;
    private AddressService addressService;

    @GetMapping
    public ModelAndView getRelativesMenu(HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                return new ModelAndView("relative-menu")
                        .addObject("relatives", relativeService.findAllByPatientId(
                                patientService.findByUserId(session.getUser().getId()).getId()))
                        .addObject("relative",new Relative())
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }

        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping
    public ModelAndView postRelatives(@ModelAttribute Relative relative, HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {
            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                System.out.println(relative);

                relative.setPatient(patientService.findByUserId(session.getUser().getId()));

                addressService.saveAddress(relative.getAddress());
                relativeService.save(relative);
                return new ModelAndView("redirect:/relativesMenu");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView deletePatient(HttpServletRequest request, @PathVariable String id) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                String schoolId = session.getUser().getHospital().getId();
                if (relativeService.checkPatientByIdAndHospitalId(id, schoolId)) {
                    relativeService.deleteById(id);
                    return new ModelAndView("redirect:/relativesMenu");
                }
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

}
