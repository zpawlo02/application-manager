package com.pawelzielinski.applicationmanager;

import com.pawelzielinski.applicationmanager.model.Application;
import com.pawelzielinski.applicationmanager.model.ApplicationHistory;
import com.pawelzielinski.applicationmanager.service.ApplicationHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApplicationHistoryTests {

    @Autowired
    private ApplicationHistoryService applicationHistoryService;
    @Test
    public void addHistory(){
        //GIVEN
        Application application = new Application("Do historii", "testuje tekst ktory zapisze sie w bazie w historii");

        //WHEN
        String appTxt = applicationHistoryService.addApplicationHistory(application).toString();

        //THAN
        assertEquals(appTxt, new ApplicationHistory(application.getId(), application.getState(), application.getTextContent()).toString());
    }
}
