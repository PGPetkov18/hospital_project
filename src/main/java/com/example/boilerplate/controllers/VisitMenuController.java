package com.example.boilerplate.controllers;

import com.example.boilerplate.exceptions.UserDoesNotHavePermissionException;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.models.Visit;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/visitsMenu")
public class VisitMenuController {
    private DoctorService doctorService;
    private SessionService sessionService;
    private CookieService cookieService;
    private RoleService roleService;
    private NavbarService navbarService;
    private VisitService visitService;
    private RelativeService relativeService;
    private PatientService patientService;


    @GetMapping
    public ModelAndView getVisitMenu(HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());

            System.out.println(session.getUser().getId());
            if (role.equals("patient")) {
                return new ModelAndView("visit-menu")
                        .addObject("visits", visitService.findAllByUserId(session.getUser().getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
            if (role.equals("receptionist")) {
                return new ModelAndView("visit-approve")
                        .addObject("visits", visitService.findAllNotApprovedByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
            if (role.equals("doctor")) {
                List<Visit> visits= visitService.findAllByUserId(session.getUser().getId());
                visits.forEach(System.out::println);
                return new ModelAndView("visit")
                        .addObject("visits",visits)
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        return null;
    }

    @GetMapping(value = "/add")
    public ModelAndView getAddForm( HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                return new ModelAndView("visit-add")
                        .addObject("visit", new Visit())
                        .addObject("doctors", doctorService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("relatives", relativeService.findAllByPatientId(
                                patientService.findByUserId(session.getUser().getId()).getId()))
                        .addObject("isAdd",true)
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        return null;
    }

    @PostMapping
    public ModelAndView saveVisit(HttpServletRequest request, @ModelAttribute Visit visit) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                visit.setApproved(false);
                visit.setPatient(patientService.findByUserId(session.getUser().getId()));
                visitService.saveVisit(visit);
                return new ModelAndView("redirect:/visitsMenu");
            }
        }
        return null;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView getUpdateVisitForm(@PathVariable String id, HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                return new ModelAndView("visit-add")
                        .addObject("visit", visitService.findById(id))
                        .addObject("doctors", doctorService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("relatives", relativeService.findAllByPatientId(
                                patientService.findByUserId(session.getUser().getId()).getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService))
                        .addObject("isAdd",false);
            }
        }
        return null;
    }


    @PostMapping(value = "/update")
    public ModelAndView putPatient(@ModelAttribute Visit visit, HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                visit.setPatient(patientService.findByUserId(session.getUser().getId()));
                visit.setApproved(false);
                visitService.update(visit);
                return new ModelAndView("redirect:/visitsMenu");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }


    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteDischargeMenuById(HttpServletRequest request, @PathVariable String id) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                visitService.deleteById(id);
                return new ModelAndView("redirect:/visitsMenu");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @GetMapping(value = "/approved")
    public ModelAndView seeAllApproved( HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("patient")) {
                return new ModelAndView("visits-approved")
                        .addObject("visits", visitService.findAllApprovedByUserId(session.getUser().getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        throw new UserDoesNotHavePermissionException();
    }
    @PostMapping(value = "/approve/{id}")
    public ModelAndView approveVisit(HttpServletRequest request, @PathVariable String id) {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("receptionist")) {
                Visit visit = visitService.findById(id);
                visit.setApproved(true);
                visitService.update(visit);
                return new ModelAndView("redirect:/visitsMenu");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }
}
