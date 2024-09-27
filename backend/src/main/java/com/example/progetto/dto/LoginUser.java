package com.example.progetto.dto;

public record LoginUser(String username, String password, String grant_type, String client_id) {
}
