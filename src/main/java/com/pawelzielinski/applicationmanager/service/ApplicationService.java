package com.pawelzielinski.applicationmanager.service;


import com.pawelzielinski.applicationmanager.model.Application;
import com.pawelzielinski.applicationmanager.model.PaginatedApplicationResponse;
import com.pawelzielinski.applicationmanager.model.State;
import com.pawelzielinski.applicationmanager.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationHistoryService applicationHistoryService;

    public Application addApplication(Application application){
        Application ap = new Application(application.getName(), application.getTextContent());
        return applicationRepository.save(ap);
    }

    public Application getApplicationById(int id){
        return applicationRepository.getReferenceById(id);
    }

    public Application editApplication(int id, String textContent){

        Application application = applicationRepository.getReferenceById(id);

        if(application.getState() == State.CREATED || application.getState() == State.VERIFIED){


            //Adding history with old data
            applicationHistoryService.addApplicationHistory(application);

            //Changing to new data
            application.setTextContent(textContent);

            return applicationRepository.save(application);
        }
        return null;
    }

    public String deleteOrRejectApplication(int id, State state, String reason){
        Application application = applicationRepository.getReferenceById(id);
        if(state == State.DELETED){
            applicationHistoryService.addApplicationHistory(application);
            application.setState(State.DELETED, reason);
            applicationRepository.save(application);
            return application + " deleted!";
        }

        applicationHistoryService.addApplicationHistory(application);
        application.setState(State.REJECTED, reason);
        applicationRepository.save(application);
        return application + " rejected!";

    }

    public String verifyApplication(int id){
        Application application = applicationRepository.getReferenceById(id);
        if(checkIfDeletedOrRejected(application)){
            applicationHistoryService.addApplicationHistory(application);
            application.setState(State.VERIFIED);
            return applicationRepository.save(application) + " verified!";
        }
        return "Not verified!";
    }

    public String acceptApplication(int id){
        Application application = applicationRepository.getReferenceById(id);
        if(checkIfDeletedOrRejected(application)){
            applicationHistoryService.addApplicationHistory(application);
            application.setState(State.ACCEPTED);
            return applicationRepository.save(application) + " accepted!";
        }
        return "Not accepted!";
    }

    public boolean checkIfDeletedOrRejected(Application application){
        return application.getState() != State.DELETED && application.getState() != State.REJECTED;
    }

    public String publishApplication(int id){
        Application application = applicationRepository.getReferenceById(id);
        if(checkIfDeletedOrRejected(application)){
            applicationHistoryService.addApplicationHistory(application);
            application.setState(State.PUBLISHED);
            application.setPublishedToken(application.getId()+1);
            return applicationRepository.save(application) + " published!";
        }
        return "Not published!";
    }

    public PaginatedApplicationResponse getApplications(String fieldName, String name, Pageable pageable){

        switch (fieldName){
            case "name":
                Page<Application> applications = applicationRepository.findAllByName(name, pageable);
                return buildResponse(applications);
            case "state":
                Page<Application> applicationsState = applicationRepository.findAllByState(StateService.getFromName(name.toUpperCase(Locale.ROOT)), pageable);
                return buildResponse(applicationsState);
        }
        return null;
    }

    public PaginatedApplicationResponse buildResponse(Page<Application> applications){
        return PaginatedApplicationResponse.builder()
                .numberOfItems(applications.getTotalElements())
                .numberOfPages(applications.getTotalPages())
                .applicationList(applications.getContent())
                .build();
    }
}
