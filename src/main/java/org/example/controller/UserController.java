package org.example.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.example.model.Category;
import org.example.model.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        if (userService.existsAccount(username, password)) {
            session.setAttribute("username", username);
            session.setAttribute("loginTime", LocalDateTime.now());
            User user = userService.findUserByUsername(username);
            Integer userId = user.getId();
            String role = user.getRole();
            session.setAttribute("userId", userId);
            session.setAttribute("role", role);
            /*
            Cart cart = cartService.getOrCreateCart(userId);
            session.setAttribute("cartId", cart.getProductId());
            if (!user.getRole().equals(Role.ADMIN.name()) && !LocalDate.now().isEqual(user.getDayConnection())) {
                userService.updateDayConnectio(username, LocalDate.now());
            }
            */
            model.addAttribute("nombre", username);
            return "redirect:/inicio";
        } else {
            model.addAttribute("error", "Usuario o contraseña invalidos");
            return "login";
        }
    }


}

