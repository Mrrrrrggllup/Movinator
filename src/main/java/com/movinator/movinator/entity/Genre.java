package com.movinator.movinator.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenre;
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Movie> movies = new ArrayList<>();

    @ManyToMany(mappedBy = "dislikes")
    @JsonIgnore
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<User> users = new ArrayList<>();

    public Genre() {
    }

    public Long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Long idGenre) {
        this.idGenre = idGenre;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}

