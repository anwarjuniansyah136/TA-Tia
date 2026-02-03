package com.rental.Inventory.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rental.Inventory.dto.request.LoginRequestDto;
import com.rental.Inventory.dto.request.RegisterRequestDto;
import com.rental.Inventory.dto.response.LoginResponseDto;
import com.rental.Inventory.entity.Users;
import com.rental.Inventory.repository.RolesRepository;
import com.rental.Inventory.repository.UserRepository;
import com.rental.Inventory.service.UserService;
import com.rental.Inventory.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final RolesRepository rolesRepository;
    
    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        Users user = userRepository.findByUsername(dto.username()).orElseThrow();
        if (user != null) {
            boolean isMatch = passwordEncoder.matches(dto.password(), user.getPassword());

            if(isMatch){
                return new LoginResponseDto(
                    user.getUsername(),
                    user.getRoles().getRoleName(),
                    jwtUtil.generateToken(user)
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid username or password");
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users register(RegisterRequestDto dto) {
        Users users = new Users();
        users.setFullName(dto.fullName());
        users.setUsername(dto.username());
        users.setPassword(passwordEncoder.encode(dto.password()));
        users.setRoles(rolesRepository.findById(dto.roleId()).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "role not found" 
            )
        ));
        users.setStatus(true);
        users.setCreateAt(LocalDateTime.now());
        users.setUpdateAt(LocalDateTime.now());
        return userRepository.save(users);
    }

    @Override
    public void deleteUsers(String id) {
        userRepository.deleteById(id);
    }

}
