package br.com.caue.barbershop.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_barber")
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "barber")
    private List<Appointment> appointments = new ArrayList<>();

    public Barber() {
    }

    public Barber(Long id, String name, Boolean isActive) {
        this.id = id;
        this.setName(name);
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Barber name is empty or null");
        }
        this.name = name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Barber barber = (Barber) o;
        return Objects.equals(id, barber.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}