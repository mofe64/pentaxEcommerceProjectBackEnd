package com.pentax.pentazon.controllers;

import com.pentax.pentazon.dtos.ApiResponse;
import com.pentax.pentazon.dtos.AuthToken;
import com.pentax.pentazon.dtos.LoginRequest;
import com.pentax.pentazon.dtos.UserDTO;
import com.pentax.pentazon.exceptions.UserException;
import com.pentax.pentazon.security.jwt.TokenProvider;
import com.pentax.pentazon.services.RoleService;
import com.pentax.pentazon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("DuplicatedCode")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostMapping("login")
    public ResponseEntity<?> generateToken( @Valid @RequestBody LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);
        return new ResponseEntity<>(new AuthToken(token), HttpStatus.OK);
    }

    @PostMapping("register/seller")
    public ResponseEntity<?> registerSeller(@Valid @RequestBody UserDTO userDTO){
        try {
            UserDTO newSeller = userService.registerSeller(userDTO);
            return new ResponseEntity<>(newSeller, HttpStatus.OK);
        } catch (UserException userException){
            return new ResponseEntity<>(new ApiResponse(false, userException.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("register/buyer")
    public ResponseEntity<?> registerBuyer(@Valid @RequestBody UserDTO userDTO){
        try {
            UserDTO newBuyer = userService.registerBuyer(userDTO);
            return new ResponseEntity<>(newBuyer, HttpStatus.OK);
        } catch (UserException userException){
            return new ResponseEntity<>(new ApiResponse(false, userException.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }));
        return errors;
    }
}
