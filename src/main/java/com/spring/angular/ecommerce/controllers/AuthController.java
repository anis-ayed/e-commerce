package com.spring.angular.ecommerce.controllers;

import com.spring.angular.ecommerce.dto.AuthenticationRequest;
import com.spring.angular.ecommerce.dto.HttpErrorResponse;
import com.spring.angular.ecommerce.dto.SignupRequest;
import com.spring.angular.ecommerce.dto.UserDto;
import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.repositories.UserRepository;
import com.spring.angular.ecommerce.services.AuthService;
import com.spring.angular.ecommerce.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/authenticate")
    public void createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authenticationRequest.getUsername(),
                                    authenticationRequest.getPassword()
                            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (user.isPresent()) {
            response.getWriter()
                    .write(
                            new JSONObject()
                                    .put("userId", user.get().getId())
                                    .put("role", user.get().getRole())
                                    .toString());
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Access-control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " +
                    "X-Requested-With, Content-Type, Accept, X-Custom-header"
            );
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }

    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>(
                    HttpErrorResponse.builder().message("User already exists").build(),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }

        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


}
