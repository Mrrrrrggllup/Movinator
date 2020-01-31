package com.movinator.movinator.controller;

import com.movinator.movinator.entity.Movie;
import com.movinator.movinator.entity.User;
import com.movinator.movinator.repository.GenreRepository;
import com.movinator.movinator.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class adminController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/adminMovie")
    public String administrator(HttpSession session, Model out) {

        out.addAttribute("genres",genreRepository.findByOrderByGenreName());
        Movie movie = new Movie();
        if (session.getAttribute("user") == null) {

            return "redirect:/";
        }
        User user = (User) session.getAttribute("user");
        out.addAttribute("user",user);
        if (user.getIsAdmin() != 1) {

            return "redirect:/";
        }

        out.addAttribute("movies", movieRepository.findByOrderByTitle());
        out.addAttribute("movie", movie);
        out.addAttribute("idMovie", 0L);
        return "administrator";
    }

    @PostMapping("/adminMovie")
    public String postAdministrator(@ModelAttribute Movie movie, @RequestParam String buttonForm, Model model,HttpSession session) {

        if (session.getAttribute("user") == null) {

            return "redirect:/";
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("genres",genreRepository.findByOrderByGenreName());
        model.addAttribute("movies",movieRepository.findByOrderByTitle());

        if (buttonForm.equals("choose")) {

            if (movie.getIdMovie() != 0) {
                model.addAttribute("movie", movieRepository.findById(movie.getIdMovie()).get());
                return "administrator";

            } else {

                model.addAttribute("movie",new Movie());
                return "administrator";
            }
        }

        movie = movieRepository.saveAndFlush(movie);
        model.addAttribute("movies",movieRepository.findByOrderByTitle());
        model.addAttribute("movie",movie);
        return "administrator";
    }
}
