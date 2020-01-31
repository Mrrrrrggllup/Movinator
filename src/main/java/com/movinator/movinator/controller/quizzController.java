package com.movinator.movinator.controller;

import com.movinator.movinator.entity.Genre;
import com.movinator.movinator.entity.Movie;
import com.movinator.movinator.entity.User;
import com.movinator.movinator.entity.UserChoice;
import com.movinator.movinator.repository.MovieRepository;
import com.movinator.movinator.repository.UserChoiceRepository;
import com.movinator.movinator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class quizzController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserChoiceRepository userChoiceRepository;

    @GetMapping("/quizz")
    public String getQuizz(Model model, @RequestParam(defaultValue = "0") int count, HttpSession session) {

        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        boolean end = false;
        if (session.getAttribute("user") == null) {

            return "redirect:/";
        }
        Movie movie = new Movie();
        do {
            Random r = new Random();
            long randomChoixe = r.nextInt(68) + 1;
           Optional<Movie> movieOp = movieRepository.findById(randomChoixe);
           if (movieOp.isPresent()) {

              end = true;
              movie = movieOp.get();
           }
        } while (movie.getGenres().size() == 0 && end);
        model.addAttribute("movie", movie);
        model.addAttribute("count", count);

        return "quizz";
    }

    @PostMapping("/quizz")
    public String postQuizz(HttpSession session, @ModelAttribute Movie movie, @RequestParam String voteButton,
                            @RequestParam int count, Model model) {


        if (session.getAttribute("user") == null) {

            return "redirect:/";
        }

        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        List<Genre> genreDislikes = userRepository.findById(user.getIdUser()).get().getDislikes();
        List<Movie> selection = new ArrayList<>();

        if (voteButton.equals("like It !") && count < 3) {

            movie = movieRepository.findById(movie.getIdMovie()).get();
            UserChoice userChoice = new UserChoice();
            userChoice.setMovie(movie);
            userChoice.setUser(user);
            userChoiceRepository.saveAndFlush(userChoice);
            session.setAttribute("user", user);

        } else if (voteButton.equals("Dislike It !") && count < 3) {

            movie = movieRepository.findById(movie.getIdMovie()).get();
            if (movie.getGenres().size() > 0) {
                genreDislikes.add(movie.getGenres().get(0));
                userRepository.saveAndFlush(user);
                session.setAttribute("user", user);
            }
        } else if (count == 3) {

            List<Movie> movies = movieRepository.findAll();
            List<UserChoice> userChoices = userChoiceRepository.findAll();
            List<Genre> genres = new ArrayList<>();

            for (UserChoice userChoice : userChoices) {

                if (userChoice.getMovie().getGenres().size() > 0 && !genreDislikes.contains(userChoice.getMovie().getGenres().get(0))) {

                    genres.add(userChoice.getMovie().getGenres().get(0));
                }

                for (Genre genre : genres) {

                    for (Movie tempMovie : movies) {

                        if (tempMovie.getGenres().size() > 0 && tempMovie.getGenres().get(0).equals(genre)) {

                            selection.add(tempMovie);
                        }
                    }
                }
            }

            for (Movie select : selection) {
                user = userRepository.findById(user.getIdUser()).get();
                user.getSelection().add(select);
                userRepository.saveAndFlush(user);
            }
            userRepository.saveAndFlush(user);
            session.setAttribute("user", user);
            return "redirect:/result";
            //TODO : delete userChoice without date of view and dislikes list
        }
        count++;
        return "redirect:/quizz?count=" + count;
    }

    @GetMapping("/showSelection")
    @ResponseBody
    public List<Movie> getSelection(HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user.getSelection().size() > 3) {
            List<Movie> selection = user.getSelection();
            List<Movie> result = new ArrayList<>();
            Random r = new Random();
            for (int i = 0, j = 0; i < 3 && j < 4; i++) {

                int random = r.nextInt(selection.size());
                if (!result.contains(selection.get(random))) {
                    result.add(selection.get(random));
                } else {
                    i--;
                    j++;
                }
            }
            return result;
        }
        return user.getSelection();
    }

    @GetMapping("/result")
    public String getResult(HttpSession session, Model model) {

        User userSession = (User) session.getAttribute("user");
        User user = userRepository.findById(userSession.getIdUser()).get();
        model.addAttribute("user", user);
        if (user.getSelection().size() > 3) {
            List<Movie> selection = user.getSelection();
            List<Movie> result = new ArrayList<>();
            Random r = new Random();
            for (int i = 0, j = 0; i < 3 && j < 400; i++) {

                int random = r.nextInt(selection.size());
                if (!result.contains(selection.get(random))) {
                    model.addAttribute("movie" + (i + 1), selection.get(random));
                    result.add(selection.get(random));
                } else {
                    i--;
                    j++;
                }
            }
        }
        List<UserChoice> userChoices = userChoiceRepository.findAll();
        for (UserChoice userChoice : userChoices) {

            if (userChoice.getRateUser() == null && userChoice.getWatchDate() == null) {

                userChoiceRepository.deleteById(userChoice.getIdChoice());
            }
        }
        user.setSelection(new ArrayList<>());
        user.setDislikes(new ArrayList<>());
        userRepository.saveAndFlush(user);
        model.addAttribute("user",user);
        return "result";
    }

    @PostMapping("/result")
    public String postResult() {

        return "redirect:/quizz?count=0";
    }
}
