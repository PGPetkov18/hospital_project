package com.example.boilerplate.controllers;

import com.example.boilerplate.models.DischargeSummary;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/dischargeSummaryMenu")
public class DischargeSummaryController {
    private DoctorService doctorService;
    private SessionService sessionService;
    private CookieService cookieService;
    private DischargeSummaryService dischargeSummaryService;
    private RoleService roleService;
    private NavbarService navbarService;
    private PatientService patientService;
    @GetMapping
    public ModelAndView getDischargeMenu(HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if(role.equals("doctor")){

                return new ModelAndView("discharge-summary-menu")
                        .addObject("discharge", new DischargeSummary())
                        .addObject("summaries",dischargeSummaryService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("patients",patientService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        return null;
    }
    @GetMapping(value = "/all")
    public ModelAndView getAllDischargeSummaries(HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if(role.equals("receptionist")||role.equals("doctor")){

                return new ModelAndView("discharge-summary")
                        .addObject("summaries",dischargeSummaryService.findAllByHospitalId(session.getUser().getHospital().getId()))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        return null;
    }

    @GetMapping(value = "/all/{id}")
    public ModelAndView getDischargeSummaries(HttpServletRequest request, @PathVariable String id){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if(role.equals("patient")||role.equals("doctor")){

                return new ModelAndView("discharge-summary")
                        .addObject("summaries",dischargeSummaryService.findAllByPatientId(id))
                        .addObject("navElements", navbarService
                                .getNavbar(cookieService.getValue(request.getCookies()), sessionService));
            }
        }
        return null;
    }






    @GetMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> downloadDischargeSummary(@PathVariable("id") String id) {
        // Retrieve the DischargeSummary object by ID
        DischargeSummary optionalSummary = dischargeSummaryService.findById(id);


            // Set the headers for a PDF file
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(optionalSummary.getFileName()).build());
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            // Return the file content as a ResponseEntity
            return new ResponseEntity<>(optionalSummary.getFile(), headers, HttpStatus.OK);

    }


    @PostMapping
    public ModelAndView postDischargeMenu(@ModelAttribute DischargeSummary dischargeSummary, @RequestParam("summary") MultipartFile file, HttpServletRequest request) throws IOException {
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if(role.equals("doctor")){
                dischargeSummary.setFileName(file.getOriginalFilename());
                dischargeSummary.setFile(file.getBytes());
                dischargeSummary.setDoctor(doctorService.findByUserId(session.getUser().getId()));
               dischargeSummaryService.saveDischargeSummary(dischargeSummary);

                return new ModelAndView("redirect:/dischargeSummaryMenu");
            }
        }
        return null;
    }
    @PostMapping(value = "/delete/{id}")
    public ModelAndView deleteDischargeMenu(@PathVariable String id, HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {

            Session session = sessionService.findById(cookieService.getValue(request.getCookies()));
            String role = roleService.getRole(session.getUser());
            if(role.equals("doctor")){

                if(dischargeSummaryService.checkDischargeSummaryByIdAndHospitalId(id,session.getUser().getHospital().getId())) {
                    dischargeSummaryService.deleteById(id);
                    return new ModelAndView("redirect:/dischargeSummaryMenu");
                }
            }
        }
        //throw error
        return null;
    }
}
