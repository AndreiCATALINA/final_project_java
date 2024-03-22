package com.final_project_java.service;

import com.final_project_java.dto.LoginDto;
import com.final_project_java.dto.RegisterDto;
import com.final_project_java.model.User;

public interface AuthService {

    User login(LoginDto loginDto);
    User register(RegisterDto registerDto);
}
