package com.example.kursach.services;

import com.example.kursach.models.Role;
import com.example.kursach.models.User;
import com.example.kursach.repositories.RoleRepository;
import com.example.kursach.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(int userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    public List<User> allUsers() {
        return (List<User>) userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByLogin(user.getLogin());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = findUserById(userId);
            for (Role role : user.getRoles()) {
                if (role.getName().equals("ADMIN")) {
                    return false;
                } else {
                    userRepository.deleteById(userId);
                    return true;
                }
            }
        }
        return false;
    }

    public User getAuthorized() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void editUser(int id, String name, String surname, int age, String login, MultipartFile file) throws IOException {
        User user = findUserById(id);
        user.setName(name);
        user.setSurname(surname);
        user.setAge(age);
        user.setLogin(login);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadFolder = new File(uploadPath);

            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            user.setFilename(resultFileName);
        }
        userRepository.save(user);
    }

    public boolean editPassword(int id, String lastPassword, String newPassword1, String newPassword2) {
        User user = findUserById(id);
        if (bCryptPasswordEncoder.matches(lastPassword, user.getPassword())) {
            if (newPassword1.equals(newPassword2)) {
                user.setPassword(bCryptPasswordEncoder.encode(newPassword1));
                userRepository.save(user);
                return true;
            } else
                return false;
        } else
            return false;
    }
}
