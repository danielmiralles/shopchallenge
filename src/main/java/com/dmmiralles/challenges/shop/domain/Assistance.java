package com.dmmiralles.challenges.shop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Assistance.
 */
@Entity
@Table(name = "assistance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assistance")
public class Assistance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "init", nullable = false)
    private Instant init;

    @NotNull
    @Column(name = "jhi_end", nullable = false)
    private Instant end;

    @ManyToOne
    private Shop shop;

    @ManyToOne
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInit() {
        return init;
    }

    public Assistance init(Instant init) {
        this.init = init;
        return this;
    }

    public void setInit(Instant init) {
        this.init = init;
    }

    public Instant getEnd() {
        return end;
    }

    public Assistance end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Shop getShop() {
        return shop;
    }

    public Assistance shop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Assistance employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assistance assistance = (Assistance) o;
        if (assistance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assistance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assistance{" +
            "id=" + getId() +
            ", init='" + getInit() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
