package com.movinator.movinator.controller;

import com.movinator.movinator.entity.Genre;
import com.movinator.movinator.entity.Movie;
import com.movinator.movinator.repository.GenreRepository;
import com.movinator.movinator.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class apiController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/movies")
    public List<Movie> getMovies() {

        return movieRepository.findAll();
    }

    @GetMapping("/movies/{idMovie}")
    public List<Movie> getAMovie(@PathVariable Long idMovie) {

        Optional<Movie> movieOp = movieRepository.findById(idMovie);
        if (movieOp.isPresent()) {
            return movieRepository.findAll();
        }
        return null;
    }

    @GetMapping("/movies/genres/{idGenre}")
    public List<Movie> getMovieGenre(@PathVariable Long idGenre) {

        Optional<Genre> genreOp = genreRepository.findById(idGenre);
        if (genreOp.isPresent()) {
            List<Movie> movies = movieRepository.findAll();
            return movies.stream().filter(n -> n.getGenres().get(0).equals(genreOp.get())).collect(Collectors.toList());
        }
        return null;
    }

    @PostMapping("/movies")
    public Movie postMovie(@RequestBody Movie movie) {

        return movieRepository.save(movie);
    }


    @PutMapping("/movies/{idMovie}")
    public Movie putMovie(@PathVariable Long idMovie, @RequestBody Movie movie) {

        Optional<Movie> movieOp = movieRepository.findById(idMovie);
        if (movieOp.isPresent()) {
            movie.setIdMovie(idMovie);
           return movieRepository.save(movie);
        }
        return null;
    }

    @DeleteMapping("/movies/{idMovie}")
    public Boolean deleteMovie(@PathVariable Long idMovie) {

        Optional<Movie> movieOp = movieRepository.findById(idMovie);
        if (movieOp.isPresent()) {
            movieRepository.deleteById(idMovie);
           return true;
        }
        return false;
    }
}
