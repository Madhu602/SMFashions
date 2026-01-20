package com.example.SMFashions.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SMFashions.Dto.AdminLoginDTO;
import com.example.SMFashions.Dto.AuthResponseDTO;
import com.example.SMFashions.Dto.SendOtpDTO;
import com.example.SMFashions.Dto.UserRegisterDTO;
import com.example.SMFashions.Dto.VerifyOtpDTO;
import com.example.SMFashions.Service.AccountService;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AccountService accountService;

    // USER REGISTER
    @PostMapping("/user/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody UserRegisterDTO dto
    ) {
        return ResponseEntity.ok(accountService.registerUser(dto));
    }

    // USER SEND OTP
    @PostMapping("/user/send-otp")
    public ResponseEntity<AuthResponseDTO> sendOtp(
            @RequestBody SendOtpDTO dto
    ) {
        return ResponseEntity.ok(accountService.sendOtp(dto.getMobile()));
    }

    // USER VERIFY OTP
    @PostMapping("/user/verify-otp")
    public ResponseEntity<AuthResponseDTO> verifyOtp(
            @RequestBody VerifyOtpDTO dto
    ) {
        return ResponseEntity.ok(
                accountService.verifyOtp(dto.getMobile(), dto.getOtp())
        );
    }

    // ADMIN LOGIN
    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponseDTO> adminLogin(
            @RequestBody AdminLoginDTO dto
    ) {
        return ResponseEntity.ok(accountService.adminLogin(dto));
    }
}
