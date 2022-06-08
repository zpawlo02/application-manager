package com.pawelzielinski.applicationmanager;


import com.pawelzielinski.applicationmanager.model.Application;
import com.pawelzielinski.applicationmanager.model.PaginatedApplicationResponse;
import com.pawelzielinski.applicationmanager.model.State;
import com.pawelzielinski.applicationmanager.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void addNewApplication(){

        //GIVEN
        Application application = new Application("Pierwsza", "testuje mala ilosc tekstu do nowego wniosku");

        //WHEN
        String appStr = applicationService.addApplication(application).toString();

        //THEN
        assertEquals(appStr, application.toString());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void editApplicationWithCreatedOrVerifiedState(){

        //GIVEN
        Application application = new Application("Do edycji", "ten tekst będzie zmodyfikowany");
        Application applicationVerified = new Application("Do edycji tez bedzie", "ten tekst będzie zmodyfikowany i wniosek ma stan Verified");

        String appText = "Zmieniam tekst stan CREATED";
        String appVerifiedText = "Zmieniam tekst VERIFIED";

        //WHEN
        int applicationId = applicationService.addApplication(application).getId();
        int applicationVerifiedId = applicationService.addApplication(applicationVerified).getId();

        String editedAppTextContent = applicationService.editApplication(applicationId, appText).getTextContent();
        String editedAppVerifiedTextContent = applicationService.editApplication(applicationVerifiedId, appVerifiedText).getTextContent();

        //THEN
        assertAll(
                () -> assertEquals(editedAppTextContent, appText),
                () -> assertEquals(editedAppVerifiedTextContent, appVerifiedText)
        );
    }

    @Test
    @Transactional
    @Rollback(false)
    public void editApplicationTextWhenStateIsOtherThanCreatedOrVerified(){
        Application application = new Application("Do edycji", "ten tekst będzie zmodyfikowany");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void deleteApplication(){
        //GIVEN
        Application application = new Application("Do usuniecia", "usune teraz ta aplikacje");

        //WHEN
        application = applicationService.addApplication(application);
        applicationService.deleteOrRejectApplication(application.getId(), State.DELETED, "Usuwam bo testuje");

        State st = applicationService.getApplicationById(application.getId()).getState();
        //THEN
        assertEquals(st, State.DELETED);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void rejectApplication(){
        //GIVEN
        Application application = new Application("Do odrzucenia", "odrzuce teraz ta aplikacje");

        //WHEN
        application = applicationService.addApplication(application);
        applicationService.deleteOrRejectApplication(application.getId(), State.REJECTED, "odrzucam bo testuje");

        State st = applicationService.getApplicationById(application.getId()).getState();
        //THEN
        assertEquals(st, State.REJECTED);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void verifyApplication(){
        //GIVEN
        Application application = new Application("Do weryfikacji", "zweryfikuje ta aplikacje");

        //WHEN
        application = applicationService.addApplication(application);
        applicationService.verifyApplication(application.getId());

        State st = applicationService.getApplicationById(application.getId()).getState();
        //THEN
        assertEquals(st, State.VERIFIED);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void acceptApplication(){
        //GIVEN
        Application application = new Application("Do akceptacji", "zaakceptuje ta aplikacje");

        //WHEN
        application = applicationService.addApplication(application);
        applicationService.acceptApplication(application.getId());

        State st = applicationService.getApplicationById(application.getId()).getState();
        //THEN
        assertEquals(st, State.ACCEPTED);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void publishApplication(){
        //GIVEN
        Application application = new Application("Do publikacji", "opublikuje ta aplikacje");

        //WHEN
        application = applicationService.addApplication(application);
        applicationService.publishApplication(application.getId());

        State st = applicationService.getApplicationById(application.getId()).getState();
        //THEN
        assertEquals(st, State.PUBLISHED);
    }

    @Test
    @Transactional
    public void getApplicationByName(){
        //GIVEN
        Application application = new Application("Nazwa do testuXSSXSSX", "ta aplikacja będzie pobierana");

        //WHEN
        applicationService.addApplication(application);
        PaginatedApplicationResponse ap = applicationService.getApplications("name", "Nazwa do testuXSSXSSX", Pageable.ofSize(1));

        //THEN
        assertEquals(ap.getApplicationList().get(0).getName(), "Nazwa do testuXSSXSSX");
    }

    @Test
    @Transactional
    public void getApplicationByState(){
        //GIVEN
        Application application = new Application("Wniosek", "ta aplikacja będzie pobierana");
        application.setState(State.ACCEPTED);
        //WHEN
        applicationService.addApplication(application);
        PaginatedApplicationResponse ap = applicationService.getApplications("state", "accepted", Pageable.ofSize(1));

        //THEN
        assertEquals(ap.getApplicationList().get(0).getState(), State.ACCEPTED);
    }
}
