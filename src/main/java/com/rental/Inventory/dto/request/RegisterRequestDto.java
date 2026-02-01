package com.rental.Inventory.dto.request;

public record RegisterRequestDto(
    String fullName,
    String password,
    String username,
    String roleId
) {
    
}
