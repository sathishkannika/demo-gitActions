package com.example.demo.controller;

import com.example.demo.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final List<User> userStore = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserController() {
        // Seed with two users for demo purposes
        userStore.add(new User(idCounter.getAndIncrement(), "sathish", "sathish@example.com"));
        userStore.add(new User(idCounter.getAndIncrement(), "kannan", "kannan@example.com"));
    }

    /** GET /api/users — return all users */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userStore);
    }

    /** GET /api/users/{id} — return one user by id */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userStore.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();

        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/users — create a new user */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        user.setId(idCounter.getAndIncrement());
        userStore.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /** PUT /api/users/{id} — update an existing user */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @Valid @RequestBody User updated) {
        for (User user : userStore) {
            if (user.getId().equals(id)) {
                user.setName(updated.getName());
                user.setEmail(updated.getEmail());
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /** DELETE /api/users/{id} — delete a user */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean removed = userStore.removeIf(u -> u.getId().equals(id));
        return removed
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
