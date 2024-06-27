package org.example.controller;


import org.springframework.ui.Model;
import org.example.model.Category;
import org.example.model.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String address,
                           @RequestParam String dni,
                           Model model) {
        // Verifica si el usuario ya existe
        if (userService.existsUserName(userName)) {
            model.addAttribute("error", "El usuario ya existe");
            return "register";
        } else {
            // Crea un nuevo usuario
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setName(name);
            user.setAddress(address);
            user.setDni(dni);
            user.setCategory(Category.LOW); // Asigna una categoría por defecto (ejemplo: LOW)
            user.setDayConnection(LocalDate.now()); // Establece la fecha actual de conexión
            user.setRole("USER"); // Establece el rol como "USER"

            // Registra el usuario utilizando el servicio UserService
            userService.userRegister(user);

            // Redirecciona al usuario a la página de login después de registrarse exitosamente
            return "redirect:/login";
        }
    }


}

