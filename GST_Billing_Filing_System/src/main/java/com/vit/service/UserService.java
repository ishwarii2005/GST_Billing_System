package com.vit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vit.entity.User;
import com.vit.exception.ResourceNotFoundException;
import com.vit.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (!user.getPassword().equals(password)) {
            throw new ResourceNotFoundException("Invalid password!");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long id, User updatedUser) {
        User existing = getUserById(id);

        existing.setBusinessName(updatedUser.getBusinessName()); // fixed
        existing.setGstin(updatedUser.getGstin());
        existing.setPan(updatedUser.getPan());
        existing.setAddress(updatedUser.getAddress());
        existing.setContact(updatedUser.getContact());
        existing.setEmail(updatedUser.getEmail());
        existing.setPassword(updatedUser.getPassword());

        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        User existing = getUserById(id);
        userRepository.delete(existing);
    }
}