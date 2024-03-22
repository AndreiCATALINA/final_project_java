package com.final_project_java.service;



import com.final_project_java.model.User;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<User> getAllCustomers();
    Optional<User> getCustomerById(Long id);
    User saveCustomer(User user);
    User updateCustomer(User user);
    void deleteCustomerById(Long id);
    List<User> getCustomersByName(String name);

}
