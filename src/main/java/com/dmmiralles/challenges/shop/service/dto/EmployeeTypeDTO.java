package com.dmmiralles.challenges.shop.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmployeeType entity.
 */
public class EmployeeTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Double salary;

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
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeTypeDTO employeeTypeDTO = (EmployeeTypeDTO) o;
        if(employeeTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", salary=" + getSalary() +
            "}";
    }
}
