package com.musobek.clickapp.controller;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.LoginDTO;
import com.musobek.clickapp.dto.RegisterDto;
import com.musobek.clickapp.entity.User;
import com.musobek.clickapp.security.JwtProvayder;
import com.musobek.clickapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private  final AuthService authService;
    private final AuthenticationManager authenticationManager;

    private  final JwtProvayder jwtProvayder;



    @PostMapping("/regist")
    public ResponseEntity<?> register( @RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.register(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(),
                    loginDTO.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvayder.generateToken(user);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse("Parol yoki login xato", false));
        }
    }
    @PutMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail( @RequestParam String email, @RequestParam String emailCode) {
        ApiResponse apiResponse = authService.verifyEmail(email, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
