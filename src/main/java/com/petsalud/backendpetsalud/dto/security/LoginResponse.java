package com.petsalud.backendpetsalud.dto.security;

import java.util.List;

public record LoginResponse(String email, List<String> authorities) {
}
