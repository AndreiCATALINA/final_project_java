package com.final_project_java.service.impl;


import com.final_project_java.model.User;
import com.final_project_java.repository.UserRepository;
import com.final_project_java.service.CustomerService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;

    public CustomerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllCustomers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getCustomerById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveCustomer(User user) {
        String password = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        user.setPassword(password);

        return userRepository.save(user);
    }

    @Override
    public User updateCustomer(User user) {
        String password = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        user.setPassword(password);

        return userRepository.save(user);
    }

    @Override
    public void deleteCustomerById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getCustomersByName(String name) {
        return userRepository.getCustomersByName(name);
    }

}
