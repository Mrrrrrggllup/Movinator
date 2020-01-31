package com.movinator.movinator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String username;
    private String password;
    @Column(unique = true)
    @Email
    private String email;
    private Integer isAdmin;

    @OneToMany(mappedBy = "user")
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonIgnore
    private List<UserChoice> userChoices = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genre_dislike",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_genre"))
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Genre> dislikes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_selection",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_movie"))
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Movie> selection = new ArrayList<>();

    public User() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<UserChoice> getUserChoices() {
        return userChoices;
    }

    public void setUserChoices(List<UserChoice> userChoices) {
        this.userChoices = userChoices;
    }

    public List<Genre> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<Genre> dislikes) {
        this.dislikes = dislikes;
    }

    public List<Movie> getSelection() {
        return selection;
    }

    public void setSelection(List<Movie> selection) {
        this.selection = selection;
    }
}

