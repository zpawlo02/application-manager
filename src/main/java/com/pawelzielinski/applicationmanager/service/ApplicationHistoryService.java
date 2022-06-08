package com.pawelzielinski.applicationmanager.service;

import com.pawelzielinski.applicationmanager.model.Application;
import com.pawelzielinski.applicationmanager.model.ApplicationHistory;
import com.pawelzielinski.applicationmanager.model.State;
import com.pawelzielinski.applicationmanager.repository.ApplicationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationHistoryService {

    @Autowired
    private ApplicationHistoryRepository applicationHistoryRepository;

    @Transactional
    public ApplicationHistory addApplicationHistory(Application oldApplication){
        State st = oldApplication.getState();
        ApplicationHistory applicationHistory = new ApplicationHistory(oldApplication.getId(), oldApplication.getState(), oldApplication.getTextContent());
        return applicationHistoryRepository.save(applicationHistory);
    }
}
