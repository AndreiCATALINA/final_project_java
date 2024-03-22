package com.final_project_java.service.impl;

import com.final_project_java.dto.LoginDto;
import com.final_project_java.dto.RegisterDto;
import com.final_project_java.exception.BadRequestException;
import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.User;
import com.final_project_java.model.UserRole;
import com.final_project_java.repository.UserRepository;
import com.final_project_java.service.AuthService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(LoginDto loginDto) {
        if (loginDto.getEmail() == null || loginDto.getEmail().isBlank()) {
            throw new BadRequestException("The email is not valid");
        }
        if (loginDto.getPassword() == null || loginDto.getPassword().isBlank()) {
            throw new BadRequestException("The password is not valid");
        }
        Optional<User> customerOptional = userRepository.getCustomerByEmail(loginDto.getEmail());
        customerOptional.orElseThrow(() ->
                new ResourceNotFoundException("The email is not registered"));

        //Verificam daca parolele se potrivesc
        //Metoda checkpw verifica daca parola primita este egala cu parola din baza de date
        boolean isMatch = BCrypt.checkpw(loginDto.getPassword(), customerOptional.get().getPassword());
        if(isMatch == false){
            throw new BadRequestException("Credentials not match");
        }
        return customerOptional.get();
    }

    @Override
    public User register(RegisterDto registerDto) {
        //validare campuri primite in registerDto
        if(registerDto.getUsername() == null || registerDto.getUsername().isBlank()){
            throw new BadRequestException("The username is invalid");
        }
        if(registerDto.getEmail() == null || registerDto.getEmail().isBlank()){
            throw new BadRequestException("The email is invalid");
        }
        if(registerDto.getPassword() == null || registerDto.getPassword().isBlank()){
            throw new BadRequestException("The password is invalid");
        }
        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
            throw new BadRequestException("Passwords not match");
        }
        //verificam sa nu mai existe un cont cu acelasi email in baza de date
        Optional<User> customerOptional = userRepository.getCustomerByEmail(registerDto.getEmail());
        if(customerOptional.isPresent()){
            throw new BadRequestException("Email already exist in db");
        }
        //pregatim obiectul de customer cu datele din ragisterDto pentru a fi salvate in baza de date
        User user = new User();
        user.setName(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());

        //metoda hashpw cripteaza parola, pe prima pozitie avem parola iar pe a doua pozitie avem "cheia de criptare"
        String password = BCrypt.hashpw(registerDto.getPassword(),BCrypt.gensalt());
        user.setPassword(password);
        user.setUserRole(UserRole.QA);

        return userRepository.save(user);
    }
}
