package com.rental.Inventory.dto.response;

public record LoginResponseDto(
    String username,
    String roleName,
    String token
) {}
