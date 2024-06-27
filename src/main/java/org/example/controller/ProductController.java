package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.model.Product;
import org.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productos")
    public String productos(HttpSession session, Model model) {
        String userID = (String) session.getAttribute("id");
        if (userID == null) {
            return "redirect:/login";
        }
        String role = (String) session.getAttribute("role");
        if (role != null && role.equals(Role.ADMIN.name())) {
            return "redirect:/admin";
        }
        List<Product> productList = productService.getAllProducts();

        model.addAttribute("products", productList);

        return "productos";
    }

    @GetMapping("/productosAdmin")
    public String productosAdmin(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "productosAdmin";
    }
    @GetMapping("/productosAdmin/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/productosAdmin/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/productosAdmin";
    }

    @GetMapping("/productosAdmin/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/productosAdmin";
    }
}
