package com.example.boilerplate.controllers;


import com.example.boilerplate.exceptions.UserDoesNotHavePermissionException;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.models.Treatment;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/treatments")
public class TreatmentController {

    private SessionService sessionService;
    private CookieService cookieService;
    private RoleService roleService;
    private NavbarService navbarService;
    private PatientService patientService;
    private TreatmentService treatmentService;
    private DoctorService doctorService;


    @GetMapping(value = "/{id}")
    public ModelAndView getMenu(HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){
                System.out.println(session.getUser().getHospital().getId());
                return new ModelAndView("treatment-menu")
                        .addObject("treatment",new Treatment())
                        .addObject("id",id)
                        .addObject("treatments",treatmentService.findByPatientId(
                                patientService.findById(id).getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping(value = "/{id}")
    public ModelAndView saveTreatment(@ModelAttribute Treatment treatment, HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {
            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){

                Patient patient = patientService.findById(id);
                treatment.setPatient(patient);
                treatment.setDoctor(doctorService.findByUserId(session.getUser().getId()));
                System.out.println(treatment);

                treatmentService.saveTreatment(treatment);
                return new ModelAndView("redirect:/treatments/"+id);
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView getUpdateForm(HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){
                System.out.println(session.getUser().getHospital().getId());
                return new ModelAndView("treatment-update")
                        .addObject("treatment",treatmentService.findById(id))
                        .addObject("id",id)
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping(value = "/update/{id}")
    public ModelAndView updateTreatment(@ModelAttribute Treatment treatment,HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){
                treatment.setDoctor(doctorService.findByUserId(session.getUser().getId()));
                treatmentService.update(treatment);
                return new ModelAndView("redirect:/treatments/"+
                        treatmentService.findById(treatment.getId()).getPatient().getId());
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteTreatment(HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){

                treatmentService.deleteById(id);
                return new ModelAndView("redirect:/patientsMenu/all");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }
}
