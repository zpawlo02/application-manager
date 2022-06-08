package com.pawelzielinski.applicationmanager.repository;

import com.pawelzielinski.applicationmanager.model.Application;
import com.pawelzielinski.applicationmanager.model.State;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Page<Application> findAllByName(String name, Pageable pageable);
    Page<Application> findAllByState(State state, Pageable pageable);
}
