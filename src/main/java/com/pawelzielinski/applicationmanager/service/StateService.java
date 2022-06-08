package com.pawelzielinski.applicationmanager.service;

import com.pawelzielinski.applicationmanager.model.State;

public class StateService {

    public static State getFromName(String name){
        switch (name){
            case "CREATED":
                return State.CREATED;
            case "DELETED":
                return State.DELETED;
            case "VERIFIED":
                return State.VERIFIED;
            case "REJECTED":
                return State.REJECTED;
            case "ACCEPTED":
                return State.ACCEPTED;
            case "PUBLISHED":
                return State.PUBLISHED;
        }
        return null;
    }
}
