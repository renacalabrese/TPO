package org.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.model.Cart;
import org.example.model.CartItem;
import org.example.model.Product;
import org.example.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getOrCreateCart(Integer id) {
        return cartRepository.findById(id).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setId(id);
            newCart.setItems(new ArrayList<>());
            return cartRepository.save(newCart);
        });
    }

    public void addToCart(Integer id,
                          Integer productId,
                          String productName,
                          int quantity,
                          float price,
                          String description) {
        Cart cart = cartRepository.findById(id).orElse(new Cart());
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }
        java.util.Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();
        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();
            Product p = new Product();
            p.setProductId(productId);
            p.setName(productName);
            p.setPrice(price);
            p.setDescription(description);
            cartItem.setProduct(p);
            cartItem.setQuantity(quantity);
            cartItem.setPrice((float) (Math.round((price * quantity) * 100) / 100d));
            cart.getItems().add(cartItem);
        }
        cart.setId(id);
        cartRepository.save(cart);
    }

    public void removeOneFromCart(Integer id, Integer productId) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));
        cart.getItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
        cartRepository.save(cart);
    }

    public void removeAllFromCart(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
    }

    public void updateCartItemQuantity(Integer id, Integer productId, int quantity, float newPrice) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setPrice(newPrice);
                });
        cartRepository.save(cart);
    }


    public List<CartItem> getCartItems(Integer id) {
        return cartRepository.findById(id)
                .map(Cart::getItems)
                .orElse(new ArrayList<>());
    }

    public void removeFromCart(Integer id, Integer productId) {
        java.util.Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> items = cart.getItems();

            items.removeIf(item -> item.getProduct().getProductId().equals(productId));

            cartRepository.save(cart);
        }
    }

    public void increaseQuantity(Integer id, Integer productId) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + id));

        List<CartItem> items = cart.getItems();
        for (CartItem item : items) {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                item.setPrice((float) (Math.round((item.getPrice() + item.getProduct().getPrice()) * 100) / 100d));
                break;
            }
        }

        cart.setItems(items);
        cartRepository.save(cart);
    }

    public void decreaseQuantity(Integer id, Integer productId) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + id));

        List<CartItem> items = cart.getItems();
        for (CartItem item : items) {
            if (item.getProduct().getProductId().equals(productId)) {
                int quantity = item.getQuantity();
                if (quantity > 1) {
                    item.setQuantity(quantity - 1);
                    item.setPrice((float) (Math.round((item.getPrice() - item.getProduct().getPrice()) * 100) / 100d));
                }
                break;
            }
        }

        cart.setItems(items);
        cartRepository.save(cart);
    }

    public Optional<CartItem> getCartItem(Integer id, Integer productId) {
        return cartRepository.findById(id)
                .flatMap(cart -> cart.getItems().stream()
                        .filter(item -> item.getProduct().getProductId().equals(productId))
                        .findFirst());
    }

    public float calculateTotalPrice(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario: " + id));

        float totalPrice = 0;
        for (CartItem item : cart.getItems()) {
            float totalPricePerItem = item.getProduct().getPrice() * item.getQuantity();
            totalPrice += totalPricePerItem;
        }
        totalPrice = Math.round(totalPrice * 100) / 100;

        return totalPrice;
    }

    public List<CartItem> calculatePrice(Integer id) {
        List<CartItem> cartItems = this.getCartItems(id);
        for (CartItem item : cartItems) {
            float totalPricePerItem = item.getProduct().getPrice() * item.getQuantity();
            item.setPrice(totalPricePerItem);
        }
        return cartItems;
    }
}