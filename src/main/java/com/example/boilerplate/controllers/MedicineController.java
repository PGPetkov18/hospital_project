package com.example.boilerplate.controllers;


import com.example.boilerplate.exceptions.UserDoesNotHavePermissionException;
import com.example.boilerplate.models.Medicine;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/medicine")
public class MedicineController {

    private SessionService sessionService;
    private CookieService cookieService;
    private RoleService roleService;
    private NavbarService navbarService;
    private PatientService patientService;
    private MedicineService medicineService;
    private DoctorService doctorService;


    @GetMapping
    public ModelAndView getMenu(HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {



            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){

                return new ModelAndView("medicine-menu")
                        .addObject("medicine",new Medicine())
                        .addObject("medicines",medicineService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping
    public ModelAndView saveMedicine(@ModelAttribute Medicine medicine, HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){
                medicine.setDoctor(doctorService.findByUserId(session.getUser().getId()));
                medicineService.saveMedicine(medicine);
                return new ModelAndView("redirect:/medicine");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }


    @GetMapping(value = "/{id}")
    public ModelAndView getMenuForAddingMedicineToPatient(HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {



            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){

                return new ModelAndView("medicine-patient")
                        .addObject("id",id)
                        .addObject("medicines",medicineService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("medicine",new Medicine())
                        .addObject("userMedicines",medicineService.findAllByPatientId(id))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping(value = "/{id}")
    public ModelAndView saveMedicineForPatient(@RequestParam("id") String medId, HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){
                Medicine medicine = medicineService.findById(medId);
                Set<Patient> patients = medicine.getPatients();
                patients.add(patientService.findById(id));
                medicine.setPatients(patients);
                medicine.setDoctor(doctorService.findByUserId(session.getUser().getId()));
                System.out.println(medicine);
                medicineService.saveMedicine(medicine);
                return new ModelAndView("redirect:/medicine/"+id);
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
                return new ModelAndView("medicine-update")
                        .addObject("medicine",medicineService.findById(id))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping(value = "/update/{id}")
    public ModelAndView updateMedicine(@ModelAttribute Medicine medicine,HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){

                medicineService.update(medicine);
                return new ModelAndView("redirect:/medicine");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteMedicine(HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){

                medicineService.deleteById(id);
                return new ModelAndView("redirect:/medicine");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }
    @PostMapping(value = "/patient/delete/{id}")
    public ModelAndView deletePatientMedicine(HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if( role.equals("doctor")){

                medicineService.deleteMappingById(id);
                return new ModelAndView("redirect:/patientsMenu/all");
            }
        }
        throw new UserDoesNotHavePermissionException();
    }
}
