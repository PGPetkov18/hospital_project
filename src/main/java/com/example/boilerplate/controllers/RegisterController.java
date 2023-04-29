package com.example.boilerplate.controllers;

import com.example.boilerplate.exceptions.InvalidCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.example.boilerplate.models.Address;
import com.example.boilerplate.models.Doctor;
import com.example.boilerplate.models.Hospital;
import com.example.boilerplate.models.User;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/register")
public class RegisterController {
    private final DoctorService doctorService;
    private final UserService userService;
    private final AddressService addressService;
    private final HospitalService hospitalService;
    private final NavbarService navbarService;
    private final CookieService cookieService;
    private final SessionService sessionService;

    @GetMapping
    public ModelAndView getRegister(HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {
            throw new RuntimeException();
        } else {
            return new ModelAndView("register")
                    .addObject("user", new User())
                    .addObject("address", new Address())
                    .addObject("hospital", new Hospital())
                    .addObject("navElements", navbarService
                            .getNavbar(cookieService.getValue(request.getCookies()), sessionService));

        }
    }
    @PostMapping
    @Transactional(rollbackFor = InvalidCredentialsException.class)
    public String postRegister(@ModelAttribute User user, @ModelAttribute Address address, @ModelAttribute Hospital hospital, HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {
            throw new RuntimeException();
        } else {
            //check if data is valid
            try {
                user.setIsAdmin(true);
                user.hashPassword();
                hospital.setAddress(address);
                user.setHospital(hospital);

                addressService.saveAddress(address);
                hospitalService.saveHospital(hospital);
                userService.saveUser(user);
                System.out.println(user);
                return "redirect:/login";

            } catch (Exception e) {
                userService.deleteUser(user);
                hospitalService.deleteHospital(hospital);
                addressService.deleteAddress(address);
                throw e;
            }
        }
    }
}
