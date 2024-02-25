package com.spring.angular.ecommerce.controllers;

import com.spring.angular.ecommerce.dto.AuthenticationRequest;
import com.spring.angular.ecommerce.dto.HttpErrorResponse;
import com.spring.angular.ecommerce.dto.SignupRequest;
import com.spring.angular.ecommerce.dto.UserDto;
import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.repositories.UserRepository;
import com.spring.angular.ecommerce.services.AuthService;
import com.spring.angular.ecommerce.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    /**
     * Authenticates a user and generates an authentication token.
     *
     * @param authenticationRequest The authentication request containing username and password.
     * @param response              The HTTP servlet response to add headers and write the token.
     * @throws IOException   If there is an issue writing to the response.
     * @throws JSONException If there is an issue creating a JSON object.
     */
    @Operation(summary = "Create authentication token",
            description = "Authenticates a user and generates an authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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


    /**
     * Signs up a new user.
     *
     * @param signupRequest The signup request containing user details.
     * @return ResponseEntity containing user information or an error response and HTTP status code.
     */
    @Operation(summary = "Sign up a new user", description = "Signs up a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User signed up successfully"),
            @ApiResponse(responseCode = "406", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
