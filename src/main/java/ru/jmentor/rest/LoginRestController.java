package ru.jmentor.rest;

import org.springframework.web.bind.annotation.*;
import ru.jmentor.dto.AuthenticationRequestDto;
import ru.jmentor.model.User;
import ru.jmentor.security.jwt.JwtAuthenticationException;
import ru.jmentor.security.jwt.JwtTokenProvider;
import ru.jmentor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/login")
public class LoginRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public LoginRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            String role = user.getRoles().toString();

            if (user == null) { throw new UsernameNotFoundException("User with username: " + username + " not found"); }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            response.put("userRole", role);
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());

            return ResponseEntity.ok(response);
        } catch (JwtAuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
