package com.pawelzielinski.applicationmanager.repository;

import com.pawelzielinski.applicationmanager.model.ApplicationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationHistoryRepository extends JpaRepository<ApplicationHistory, Integer> {
}
