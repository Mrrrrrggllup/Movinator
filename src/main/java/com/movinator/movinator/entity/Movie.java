package com.movinator.movinator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovie;
    @Column(columnDefinition = "text")
    private String title;
    @Column(columnDefinition = "text")
    private String occasion;
    @Column(columnDefinition = "text")
    private String pitch;
    @JsonProperty("release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    private String director;
    @JsonProperty("original_language")
    private String originalLanguage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_actor"))
    @Cascade(CascadeType.ALL)
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_genre"))
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany(mappedBy = "selection")
    @JsonIgnore
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonIgnore
    private List<UserChoice> userChoices = new ArrayList<>();

    public Movie() {
    }

    public Long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Long idMovie) {
        this.idMovie = idMovie;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }


    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<UserChoice> getUserChoices() {
        return userChoices;
    }

    public void setUserChoices(List<UserChoice> userChoices) {
        this.userChoices = userChoices;
    }
}

