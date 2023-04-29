package com.example.boilerplate.controllers;


import com.example.boilerplate.services.CookieService;
import com.example.boilerplate.services.NavbarService;
import com.example.boilerplate.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class WebController {

    private final NavbarService navbarService;
    private final CookieService cookieService;
    private final SessionService sessionService;
    @GetMapping(value = "/")
    public ModelAndView getIndex(HttpServletRequest request) {
        return new ModelAndView("index")
                .addObject("navElements", navbarService
                        .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
    }
}
