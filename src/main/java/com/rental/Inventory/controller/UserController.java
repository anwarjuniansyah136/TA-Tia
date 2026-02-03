package com.rental.Inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.rental.Inventory.dto.GenericResponse;
import com.rental.Inventory.dto.request.LoginRequestDto;
import com.rental.Inventory.dto.request.RegisterRequestDto;
import com.rental.Inventory.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    
    @PostMapping("/signin")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto dto){
        try {
            return ResponseEntity.ok().body(GenericResponse.success(userService.login(dto), "successfully"));
        } 
        catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDto dto){
        try {
            return ResponseEntity.ok().body(GenericResponse.success(userService.register(dto), "successfully"));
        } catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/find-all")
    public ResponseEntity<Object> findAllUsers(){
        try{
            return ResponseEntity.ok().body(GenericResponse.success(userService.getAllUsers(),"fuck"));
        }catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUsers(@PathVariable String id){
        userService.deleteUsers(id);
        return ResponseEntity.noContent().build();
    }
}
