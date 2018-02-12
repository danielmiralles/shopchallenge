package com.dmmiralles.challenges.shop.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Assistance entity.
 */
public class AssistanceDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant init;

    @NotNull
    private Instant end;

    private Long shopId;

    private String shopName;

    private Long employeeId;

    private String employeeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInit() {
        return init;
    }

    public void setInit(Instant init) {
        this.init = init;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssistanceDTO assistanceDTO = (AssistanceDTO) o;
        if(assistanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assistanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssistanceDTO{" +
            "id=" + getId() +
            ", init='" + getInit() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
