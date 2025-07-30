package codeworld.projectjava.registration.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    private final Map<String, String> refreshTokenStore = new ConcurrentHashMap<>();

    public void storeRefreshToken(String username, String refreshToken) {
        refreshTokenStore.put(username, refreshToken);
    }

    public String getRefreshToken(String username) {
        return refreshTokenStore.get(username);
    }

    public boolean isRefreshTokenValid(String username, String refreshToken) {
        return refreshToken.equals(refreshTokenStore.get(username));
    }

    public void revokeRefreshToken(String username) {
        refreshTokenStore.remove(username);
    }
} 
