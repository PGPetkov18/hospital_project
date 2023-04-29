package com.example.boilerplate.controllers;

import com.example.boilerplate.exceptions.UserDoesNotHavePermissionException;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Session;

import com.example.boilerplate.models.User;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/patientsMenu")
@AllArgsConstructor
public class PatientMenuController {
    private PatientService patientService;
    private SessionService sessionService;
    private CookieService cookieService;
    private RoleService roleService;
    private UserService userService;
    private NavbarService navbarService;

    @GetMapping()
    public ModelAndView getMenu(HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("receptionist")){
                System.out.println(session.getUser().getHospital().getId());
                return new ModelAndView("patient-menu")
                        .addObject("patient",new Patient())
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService))
                        .addObject("patients",patientService.findAllByHospitalId(session.getUser().getHospital().getId()));
            }
        }
        return null;
    }

    @GetMapping(value = "/all")
    public ModelAndView getPatients(HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if(role.equals("doctor") || role.equals("receptionist")){
                System.out.println(session.getUser().getHospital().getId());
                return new ModelAndView("patients")
                        .addObject("patients",patientService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        return null;
    }
    @PostMapping()
    public ModelAndView postPatient(@ModelAttribute Patient patient, HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {
            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("receptionist")) {

                patient.getUser().setHospital(session.getUser().getHospital());
                patient.getUser().hashPassword();
                userService.saveUser(patient.getUser());
                patientService.savePatient(patient);
                return new ModelAndView("redirect:/patientsMenu");
            }
        }
        //throw exception
        return null;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView getUpdatePatient(@PathVariable String id, HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("receptionist")) {

                return new ModelAndView("patient-menu-update")
                        .addObject("patient", patientService.findById(id))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
            if(role.equals("doctor")){
                return new ModelAndView("patient-update-doctor")
                        .addObject("patient", patientService.findById(id))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        throw new UserDoesNotHavePermissionException();
    }
    @PostMapping(value = "/update/{id}")
    public ModelAndView updatePatient(@ModelAttribute Patient patient,@PathVariable String id, HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("receptionist")) {
                System.out.println(patient.getUser());

                Patient patientDb = patientService.findById(id);
                User userDb = patientDb.getUser();

                User user =patient.getUser();
                userService.update(user.getUsername(),user.getFirstName(), user.getLastName(), userDb.getId());
                System.out.println(userService.findById(userDb.getId()));
                patientService.update(patient,id);
                return new ModelAndView("redirect:/patientsMenu");
            }
            if(role.equals("doctor")){
                patientService.updateFromDoctor(patient);
                return new ModelAndView("redirect:/patientsMenu/all");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView deletePatient( HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("receptionist")) {
                String hospitalId= session.getUser().getHospital().getId();
                if( patientService.checkPatientByIdAndHospitalId(id,hospitalId)){
                    patientService.deleteById(id);
                    return new ModelAndView("redirect:/patientsMenu");
                }
            }
        }
        // throw new UserDoesNotHavePermissionException();
        return null;
    }
}
