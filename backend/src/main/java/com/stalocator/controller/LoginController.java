package com.stalocator.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stalocator.dto.AdminDTO;
import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.AuthDTO;
import com.stalocator.dto.CustomerDTO;
import com.stalocator.dto.OwnerDTO;
import com.stalocator.entities.Role;
import com.stalocator.service.AdminServiceImpl;
import com.stalocator.service.CustomerServiceImpl;
import com.stalocator.service.LoginServiceImpl;
import com.stalocator.service.OwnerService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    LoginServiceImpl loginServiceImpl;

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @Autowired
    AdminServiceImpl adminServiceImpl;

    @Autowired
    OwnerService ownerService;

    @PostMapping
    public ResponseEntity<?> userSignIn(@RequestBody AuthDTO dto) {
        try {
            Role role = loginServiceImpl.findRole(dto);
            if (role.equals(Role.ADMIN)) {
                AdminDTO authenticatedUser = adminServiceImpl.authenticateUser(dto);
                String token = generateToken(authenticatedUser.getEmail(), role);
                session.setAttribute("user", authenticatedUser);
                session.setAttribute("role", "ADMIN");
                return ResponseEntity.ok(new AuthResponse(authenticatedUser, token));
            } else if (role.equals(Role.CUSTOMER)) {
                CustomerDTO authenticatedUser = customerServiceImpl.authenticateUser(dto);
                String token = generateToken(authenticatedUser.getEmail(), role);
                System.out.println(token);
                session.setAttribute("user", authenticatedUser);
                session.setAttribute("role", "CUSTOMER");
                System.out.println(session);
                customerServiceImpl.setToken(authenticatedUser.getId(), token);
                return ResponseEntity.ok(new AuthResponse(authenticatedUser, token));
            } else if (role.equals(Role.OWNER)) {
                OwnerDTO authenticatedUser = ownerService.authenticateUser(dto);
                session.setAttribute("user", authenticatedUser);
                session.setAttribute("role", "OWNER");
                String token = generateToken(authenticatedUser.getEmail(), role);
                return ResponseEntity.ok(new AuthResponse(authenticatedUser, token));
            } else {
                throw new RuntimeException("Invalid role");
            }
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage()));
        }
    }

    private String generateToken(String email, Role role) {
        String secretKey = "your_secret_key_here";
        long tokenExpirationTime = 3600000; // 1 hour

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Inner class to wrap the response with token
    public static class AuthResponse {
        private Object user;
        private String token;

        public AuthResponse(Object user, String token) {
            this.user = user;
            this.token = token;
        }

        public Object getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }
    }
}
