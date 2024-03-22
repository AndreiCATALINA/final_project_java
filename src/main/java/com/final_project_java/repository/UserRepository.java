package com.final_project_java.repository;


import com.final_project_java.model.Project;
import com.final_project_java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getCustomersByName(String name);
    Optional<User> getCustomerByEmail(String email);

    List<User> getUsersByProjectIsNull();

}
