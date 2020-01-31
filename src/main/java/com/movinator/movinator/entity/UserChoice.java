package com.movinator.movinator.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChoice;

    private Float rateUser;
    private Float accuracy;
    @Temporal(TemporalType.DATE)
    private Date watchDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "id_movie")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "id_user")
    private User user;

    public UserChoice() {
    }

    public Long getIdChoice() {
        return idChoice;
    }

    public void setIdChoice(Long idChoice) {
        this.idChoice = idChoice;
    }

    public Float getRateUser() {
        return rateUser;
    }

    public void setRateUser(Float rateUser) {
        this.rateUser = rateUser;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float precision) {
        this.accuracy = precision;
    }

    public Date getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(Date watchDate) {
        this.watchDate = watchDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}