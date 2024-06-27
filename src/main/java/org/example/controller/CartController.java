package org.example.controller;

import java.util.List;
import java.util.Optional;

import org.example.model.CartItem;
import org.example.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartService.getCartItems(id);
        float totalPrice = cartService.calculateTotalPrice(id);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Integer productId,
                            @RequestParam String productName,
                            @RequestParam int quantity,
                            @RequestParam float price,
                            HttpSession session) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }

        Optional<CartItem> existingItem = cartService.getCartItem(id, productId);
        if (existingItem.isPresent()) {
            int newQuantity = existingItem.get().getQuantity() + quantity;
            float newPrice = existingItem.get().getPrice() + price * quantity;
            cartService.updateCartItemQuantity(id, productId, newQuantity, newPrice);
        } else {
            cartService.addToCart(id, productId, productName, quantity, price, "");
        }

        System.out.println("Adding to cart - UserId: " + id + ", ProductID: " + productId +
                ", ProductName: " + productName + ", Quantity: " + quantity + ", Price: " + price);

        return "redirect:/productos";
    }


    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Integer productId,
                                 Model model,
                                 HttpSession session) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }

        cartService.removeFromCart(id, productId);

        return "redirect:/cart";
    }

    @PostMapping("/cart/increase")
    public String increaseQuantity(@RequestParam Integer productId,
                                   Model model,
                                   HttpSession session) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }
        cartService.increaseQuantity(id, productId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/decrease")
    public String decreaseQuantity(@RequestParam Integer productId,
                                   Model model,
                                   HttpSession session) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }
        cartService.decreaseQuantity(id, productId);
        return "redirect:/cart";
    }

    @PostMapping("/cart")
    public String priceCalculator(Model model, HttpSession session) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartService.calculatePrice(id);

        model.addAttribute("cartItems", cartItems);

        return "cart";
    }
}