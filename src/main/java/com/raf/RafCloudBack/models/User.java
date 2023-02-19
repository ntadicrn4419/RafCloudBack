package com.raf.RafCloudBack.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String firstname;

    @Column
    @NotNull
    private String lastname;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column
    @NotNull
    @JsonIgnore
    private String password;

    @Column
    @ElementCollection
    private List<UserPermission> permissions;

    @Column
    @Version
    private Integer version = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserPermission> getPermissionList() {
        return permissions;
    }

    public void setPermissionList(List<UserPermission> userPermissionList) {
        this.permissions = userPermissionList;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
