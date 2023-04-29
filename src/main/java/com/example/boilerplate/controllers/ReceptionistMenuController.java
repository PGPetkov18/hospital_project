package com.example.boilerplate.controllers;

import com.example.boilerplate.exceptions.UserDoesNotHavePermissionException;
import com.example.boilerplate.models.Doctor;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Receptionist;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "receptionistsMenu")
@AllArgsConstructor
public class ReceptionistMenuController {
    private ReceptionistService receptionistService;
    private SessionService sessionService;
    private CookieService cookieService;
    private RoleService roleService;
    private UserService userService;
    private NavbarService navbarService;

    @GetMapping
    public ModelAndView getReceptionistMenu(HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if(role.equals("admin")){
                System.out.println(session.getUser().getHospital().getId());


                return new ModelAndView("receptionist-menu")
                        .addObject("receptionists",receptionistService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("receptionist", new Receptionist())
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        return null;
    }
    @PostMapping
    public ModelAndView postReceptionist(@ModelAttribute Receptionist receptionist, HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {
            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("admin")) {

                receptionist.getUser().setHospital(session.getUser().getHospital());
                receptionist.getUser().hashPassword();
                userService.saveUser(receptionist.getUser());
                receptionistService.saveReceptionist(receptionist);
                return new ModelAndView("redirect:/receptionistsMenu");
            }
        }
        //throw exception
        return null;
    }


    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteReceptionist( HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if (role.equals("admin")) {
                String hospitalId= session.getUser().getHospital().getId();
                if( receptionistService.checkReceptionistByIdAndHospitalId(id,hospitalId)){
                    receptionistService.deleteById(id);
                    return new ModelAndView("redirect:/receptionistsMenu");
                }
            }
        }
        // throw new UserDoesNotHavePermissionException();
        return null;
    }

}
