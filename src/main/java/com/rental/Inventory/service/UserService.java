package com.rental.Inventory.service;

import java.util.List;

import com.rental.Inventory.dto.request.LoginRequestDto;
import com.rental.Inventory.dto.request.RegisterRequestDto;
import com.rental.Inventory.dto.response.LoginResponseDto;
import com.rental.Inventory.entity.Users;

public interface UserService {
    public LoginResponseDto login(LoginRequestDto dto);
    public List<Users> getAllUsers();
    public Users register(RegisterRequestDto dto);
    void deleteUsers(String id);
}
