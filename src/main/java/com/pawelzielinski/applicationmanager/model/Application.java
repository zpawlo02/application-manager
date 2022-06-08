package com.pawelzielinski.applicationmanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String textContent;

    @Enumerated(EnumType.STRING)
    private State state;

    private String reasonOfDelRej;

    private int publishedToken;


    public Application(String name, String textContent) {
        this.name = name;
        this.textContent = textContent;
        state = State.CREATED;
        reasonOfDelRej = "";
    }

    public void setState(State state){
        if(this.state == State.CREATED || this.state == State.VERIFIED){
            this.state = state;
        }
    }

    public void setState(State state, String reasonOfDelRej){
        this.state = state;
        this.reasonOfDelRej = reasonOfDelRej;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
