package com.example.boilerplate.controllers;

import com.example.boilerplate.exceptions.InvalidCredentialsException;
import com.example.boilerplate.exceptions.UserDoesNotHavePermissionException;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.models.User;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login")
@AllArgsConstructor
public class LoginController {
    private final UserService userService;
    private final SessionService sessionService;
    private final NavbarService navbarService;

    private final CookieService cookieService;
    private final RoleService roleService;

    @GetMapping
    public ModelAndView getLogin(HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {
            throw new UserDoesNotHavePermissionException();
        } else {
            return new ModelAndView("/login")
                    .addObject("user", new User())
                    .addObject("navElements",
                            navbarService.getNavbar(cookieService.getValue(request.getCookies()), sessionService));
        }
    }

    @GetMapping(value = "/logout")
    public String getLogout(HttpServletRequest request,HttpServletResponse response) {
        sessionService.deleteById(cookieService.getValue(request.getCookies()));
        Cookie cookie = new Cookie("session", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }


    @PostMapping
    public ModelAndView postLogin(@ModelAttribute User loginUser, HttpServletResponse response, HttpServletRequest request) {
        if (cookieService.isSessionPresent(request.getCookies())) {
            throw new UserDoesNotHavePermissionException();
        } else {
            User user = userService.findByUsername(loginUser.getUsername());

            if (user != null) {

                loginUser.setSalt(user.getSalt());
                loginUser.hashPassword();

                if (userService.checkPassword(user, loginUser)) {
                    System.out.println("Password was correct");
                    String id = UUID.randomUUID().toString();

                    Session temp = sessionService.findByUserId(user.getId());
                    //check if user already has a session
                    if (temp != null) {
                        sessionService.deleteSession(temp);
                    }
                    Session session = new Session(id, roleService.getRole(user), LocalDateTime.now(), user);
                    if (sessionService.saveSession(session) != null) {
                        System.out.println("creating a cookie");
                        Cookie cookie = new Cookie("session", id);
                        cookie.setMaxAge(4 * 60 * 60);
                        cookie.setHttpOnly(true);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        return new ModelAndView("redirect:/");
                    }
                }
            }
            throw new InvalidCredentialsException();
        }
    }
}
