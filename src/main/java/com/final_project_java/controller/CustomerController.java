package com.final_project_java.controller;


import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.User;

import com.final_project_java.service.CustomerService;
import com.final_project_java.utils.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping //http://localhost:8081/api/customers
    public ResponseEntity<ApiResponse> getAllCustomers() {
        List<User> customersList = customerService.getAllCustomers();
        if (customersList.isEmpty()) {
            throw new ResourceNotFoundException("The DB doesn't contain any customers");
        }
//        ApiResponse response = new ApiResponse.Builder()
//                .status(HttpStatus.OK.value())
//                .message("Customer list")
//                .data(customersList)
//                .build();
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return ResponseEntity.ok(ApiResponse.success("Customer list",customersList));
    }

    @GetMapping("/customerById/{id}") //http://localhost:8081/api/customers/customersById/{id}
    public ResponseEntity<ApiResponse> getAllCustomerById(@PathVariable Long id) {
        Optional<User> customerById = customerService.getCustomerById(id);
        customerById.orElseThrow(() ->
                new ResourceNotFoundException("The customer with id : " + id + " doesn't exist in DB"));
        return ResponseEntity.ok(ApiResponse.success("Customer by id",customerById.get()));
    }

    @GetMapping("/customersByName/{name}") //http://localhost:8081/api/customers/customersByName/{name}
    public ResponseEntity<ApiResponse> getAllCustomersByName(@PathVariable String name) {
        List<User> customersByName = customerService.getCustomersByName(name);
        if (customersByName.isEmpty()) {
            throw new ResourceNotFoundException("The client with name : " + name + " doesn't exist in DB");
        }
        return ResponseEntity.ok(ApiResponse.success("Customer by name",customersByName));
    }

    @PostMapping("/addNewCustomer") //http://localhost:8081/api/customers/addNewCustomer
    public ResponseEntity<ApiResponse> saveCustomer(@RequestBody User user) {
        User user1 = customerService.saveCustomer(user);
        return ResponseEntity.ok(ApiResponse.success("Add customer", user1));
    }

    @PutMapping("/updateCustomer") //http://localhost:8081/api/customers/updateCustomer
    public ResponseEntity<ApiResponse> updateCustomer(@RequestBody User user) {
        if(user.getId() == null){
            throw new ResourceNotFoundException("Customer id is not valid");
        }
        Optional<User> customerOptional = customerService.getCustomerById(user.getId());
        customerOptional.orElseThrow(()->
                new ResourceNotFoundException("Customer with id: " + user.getId() + " doesn't exist in DB"));
        return ResponseEntity.ok(ApiResponse.success("Update customer",customerService.updateCustomer(user)));
    }

    @DeleteMapping("/deleteCustomerById/{id}") //http://localhost:8081/api/customers/deleteCustomerById/{id}
    public ResponseEntity<ApiResponse> deleteCustomerById(@PathVariable Long id) {
        Optional<User> customerOptional = customerService.getCustomerById(id);
        customerOptional.orElseThrow(() ->
                new ResourceNotFoundException("The customer with id : " + id + " doesn't exist in DB"));
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok(ApiResponse.success("Customer with id: " + id + " deleted successfully",null));
    }


}
