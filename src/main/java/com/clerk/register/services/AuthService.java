package com.clerk.register.services;

import com.clerk.register.data.requests.LoginRequest;
import com.clerk.register.data.responses.LoginResponse;
import com.clerk.register.models.User;
import com.clerk.register.security.ClerkUserPrincipal;
import com.clerk.register.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password())
            );

            return issueFor(((ClerkUserPrincipal) authentication.getPrincipal()).user());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    public LoginResponse issueFor(User user) {
        return new LoginResponse(jwtService.issueJwt(user), user.getId(), user.getRole().name());
    }
}
