package com.example.boilerplate.controllers;


import com.example.boilerplate.exceptions.UserDoesNotHavePermissionException;
import com.example.boilerplate.models.Doctor;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/doctorsMenu")
@AllArgsConstructor
public class DoctorMenuController {
    private DoctorService doctorService;
    private SessionService sessionService;
    private CookieService cookieService;
    private RoleService roleService;
    private UserService userService;
    private NavbarService navbarService;
    private DischargeSummaryService dischargeSummaryService;

    @GetMapping
    public ModelAndView getDoctorsMenu(HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if(role.equals("admin")){
                System.out.println(session.getUser().getHospital().getId());
                return new ModelAndView("doctor-menu")
                        .addObject("doctors",doctorService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("doctor", new Doctor())
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        return null;
    }
    @PostMapping
    public ModelAndView postDoctor(@ModelAttribute Doctor doctor, HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {
            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("admin")) {

                doctor.getUser().setHospital(session.getUser().getHospital());
                doctor.getUser().hashPassword();
                userService.saveUser(doctor.getUser());
                doctorService.saveDoctor(doctor);
                return new ModelAndView("redirect:/doctorsMenu");
            }
        }
        //throw exception
        return null;
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteDoctor( HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("admin")) {
                String hospitalId= session.getUser().getHospital().getId();
                if( doctorService.checkDoctorByIdAndHospitalId(id,hospitalId)){
                    dischargeSummaryService.deleteByDoctorId(id);
                    doctorService.deleteById(id);
                    return new ModelAndView("redirect:/doctorsMenu");
                }
            }
        }
        // throw new UserDoesNotHavePermissionException();
        return null;
    }

}
