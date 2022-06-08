package com.pawelzielinski.applicationmanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
@Getter
@Setter
@RequiredArgsConstructor
public class ApplicationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idApplication;
    @Enumerated(EnumType.STRING)
    private State historicalState;
    private String textContent;
    private Date dateOfChange;

    public ApplicationHistory(int idApplication, State historicalState, String textContent) {
        this.idApplication = idApplication;
        this.historicalState = historicalState;
        this.textContent = textContent;
        dateOfChange = new Date();
    }

    @Override
    public String toString() {
        return "idApplication: " + idApplication + " state: " + historicalState + " textContent: " + textContent;
    }
}
