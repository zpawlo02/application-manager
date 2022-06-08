package com.pawelzielinski.applicationmanager.controller;

import com.pawelzielinski.applicationmanager.model.Application;
import com.pawelzielinski.applicationmanager.model.State;
import com.pawelzielinski.applicationmanager.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    @PostMapping("/addApplication")
    public String addApplication(@RequestBody Application application){
        return applicationService.addApplication(application).toString();
    }

    @PatchMapping("/editApplication/{id}")
    public String editApplication(@RequestBody String textContent, @PathVariable Integer id){
        return applicationService.editApplication(id, textContent).toString();
    }

    @PatchMapping("/deleteApplication/{id}")
    public String deleteApplication(@RequestBody String reason, @PathVariable Integer id){
        return applicationService.deleteOrRejectApplication(id, State.DELETED, reason);
    }

    @PatchMapping("/rejectApplication/{id}")
    public String rejectApplication(@RequestBody String reason, @PathVariable Integer id){
        return applicationService.deleteOrRejectApplication(id, State.REJECTED, reason);
    }

    @PatchMapping("/verifyApplication/{id}")
    public String verifyApplication(@PathVariable Integer id){
        return applicationService.verifyApplication(id);
    }

    @PatchMapping("/acceptApplication/{id}")
    public String acceptApplication(@PathVariable Integer id){
        return applicationService.acceptApplication(id);
    }

    @PatchMapping("/publishApplication/{id}")
    public String publishApplication(@PathVariable Integer id){
        return applicationService.publishApplication(id);
    }

    @GetMapping("/getApplicationsBy")
    public String getApplicationsByName(@RequestParam("fieldName") String fieldName, @RequestParam("query") String query, Pageable pageable){
        return applicationService.getApplications(fieldName, query, pageable).getApplicationList().toString();
    }


    @RequestMapping("/")
    public String home(){
        return "Hello";
    }
}
