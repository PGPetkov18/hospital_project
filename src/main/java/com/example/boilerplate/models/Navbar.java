package com.example.boilerplate.models;

import java.util.HashMap;
import java.util.Map;

public class Navbar {
    private Map<String, String> elements;

    public Navbar() {
        elements = new HashMap<>();
    }

    public Navbar(Map<String, String> elements) {
        this.elements = elements;
    }

    public Map<String, String> getElements() {
        return elements;
    }

    public void setElements(Map<String, String> elements) {
        this.elements = elements;
    }
}
