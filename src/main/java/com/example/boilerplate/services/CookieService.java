package com.example.boilerplate.services;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    public String getValue(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    public boolean isSessionPresent(Cookie[] cookies) {
        return !getValue(cookies).equals("");
    }
}

