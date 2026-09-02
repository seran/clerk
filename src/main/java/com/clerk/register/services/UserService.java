package com.clerk.register.services;

import com.clerk.register.data.responses.UserResponse;
import com.clerk.register.data.responses.UserResponseLegacy;
import com.clerk.register.exceptions.ResourceNotFoundException;
import com.clerk.register.models.Role;
import com.clerk.register.models.User;
import com.clerk.register.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse findById(Long id) {
        return UserResponse.from(findUserById(id));
    }

    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", username));
    }

    @Transactional
    public User createUser(User user) {
        user.setHashed_password(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public UserResponse updateRole(Long id, Role role) {
        User user = findUserById(id);
        user.setRole(role);
        return UserResponse.from(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        userRepository
                .delete(findUserById(id));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }
}
