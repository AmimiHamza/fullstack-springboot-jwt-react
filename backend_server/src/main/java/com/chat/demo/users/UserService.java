package com.chat.demo.users;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    
    private User getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User userinfo() {
        User user =(User) this.getCurrentUser();
        return user;
    }

    public ResponseEntity<String> saveUser(User user) {
        try {
            // Call the service method to save the user
            userRepository.save(user);
            return ResponseEntity.ok("User saved successfully.");
        } catch (Exception e) {
            // Return an error response if saving the user fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save user: " + e.getMessage());
        }
    }
}
