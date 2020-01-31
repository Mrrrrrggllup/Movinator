package com.movinator.movinator.controller;

import com.movinator.movinator.entity.User;
import com.movinator.movinator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class navigationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String getIndex(HttpSession session, Model out) {

        out.addAttribute("error", false);
        if (session.getAttribute("user") != null) {

            return "redirect:/quizz";
        }
        return "index";
    }

    @PostMapping("/")
    public String postIndex(@RequestParam(defaultValue = "") String email, @RequestParam String username, @RequestParam String password,
                            @RequestParam(defaultValue = "") String register, HttpSession session, Model out) {

        out.addAttribute("error", false);
        if (session.getAttribute("user") != null) {

            return "redirect:/quizz";
        }

        if (register.equals("register")) {

            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user = userRepository.save(user);
            session.setAttribute("user", user);
            return "redirect:/quizz";

        } else {

            Optional<User> userOp = userRepository.findByUsernameAndPassword(username, password);
            if (userOp.isPresent()) {
                session.setAttribute("user", userOp.get());
                return "redirect:/quizz";
            } else {

                out.addAttribute("error", true);
            }
        }
        return "index";
    }

    @GetMapping("/disconnect")
    public String getDisconnect(HttpSession session) {

        session.removeAttribute("user");
        return "redirect:/";
    }
}
