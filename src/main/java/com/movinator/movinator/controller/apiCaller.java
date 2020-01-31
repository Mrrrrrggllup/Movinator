package com.movinator.movinator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movinator.movinator.entity.Actor;
import com.movinator.movinator.entity.Genre;
import com.movinator.movinator.entity.Movie;
import com.movinator.movinator.repository.ActorRepository;
import com.movinator.movinator.repository.GenreRepository;
import com.movinator.movinator.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class apiCaller {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    ActorRepository actorRepository;

    @GetMapping("/combler-la-base")
    @ResponseBody
    public Movie getMovies() {
        //https://api.themoviedb.org/3/movie/550?api_key=7d9029856424385f0a1be0303019d9a6
        for (int i = 1, j = 1; i < 200; i++, j++) {
            String url = "https://api.themoviedb.org/3/person/" + i;

            try {
                WebClient webClient = WebClient.create(url);
                Mono<String> call = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("api_key", "7d9029856424385f0a1be0303019d9a6")
                                .build())
                        .retrieve()
                        .bodyToMono(String.class);

                String response = call.block();
                Movie movie = new Movie();
                Genre genre = new Genre();
                Actor actor = new Actor();

                try {
                    List<Movie> movies = movieRepository.findAll();
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode root = objectMapper.readTree(response);
                    String job = objectMapper.convertValue(root.get("known_for_department"), String.class);
                    if (job.equals("Acting")) {
                        actor.setName(objectMapper.convertValue(root.get("name"), String.class));
                        actor = actorRepository.saveAndFlush(actor);
                    }

                    for (int g = 1; g < movies.size(); g++) {

                        movie = movies.get(g);
                        if (objectMapper.convertValue(root.get("biography"), String.class).contains(movie.getTitle()) && job.equals("Acting")) {

                            movie.getActors().add(actor);
                            movieRepository.save(movie);

                        } else if (job.equals("Directing") && objectMapper.convertValue(root.get("biography"), String.class).contains(movie.getTitle())) {

                            movie.setDirector(objectMapper.convertValue(root.get("name"), String.class));
                            movieRepository.save(movie);
                        }
                    }



                    /*String genreName = objectMapper.convertValue(root.get("genres").get(0).get("name"), String.class);
                    genre = genreRepository.findByGenreName(genreName);
                    movie.getGenres().add(genre);
                    movieRepository.save(movie);*/
                   /* movie.setTitle(objectMapper.convertValue(root.get("title"), String.class));
                    movie.setPitch(objectMapper.convertValue(root.get("overview"), String.class));
                    movie.setReleaseDate((objectMapper.convertValue(root.get("release_date"), Date.class)));
                    movie.setOriginalLanguage((objectMapper.convertValue(root.get("original_language"), String.class)));
                    movieRepository.save(movie);*/

                    //String genreNAme = objectMapper.convertValue(root.get("genres").get(0).get("name"), String.class);
                 /*   if (!genreRepository.findAll().stream().map(n -> n.getGenreName()).collect(Collectors.toList()).contains(genreNAme)) {
                        genre.setGenreName(genreNAme);
                        genreRepository.save(genre);
                    }*/
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                continue;
            }

        }
        return movieRepository.findById(3L).

                get();
    }
}
