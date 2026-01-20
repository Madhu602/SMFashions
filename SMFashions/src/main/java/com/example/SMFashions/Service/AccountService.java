package com.example.SMFashions.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SMFashions.Dto.AdminLoginDTO;
import com.example.SMFashions.Dto.AuthResponseDTO;
import com.example.SMFashions.Dto.UserRegisterDTO;
import com.example.SMFashions.Entity.Account;
import com.example.SMFashions.Enum.Role;
import com.example.SMFashions.Repository.AccountRepository;

@Service
public class AccountService {

    private static final String DUMMY_OTP = "123456";

    @Autowired
    private AccountRepository accountRepository;

    // 1️⃣ USER REGISTRATION (ONE TIME)
    public AuthResponseDTO registerUser(UserRegisterDTO dto) {

        if (accountRepository.existsByMobile(dto.getMobile())) {
            return new AuthResponseDTO(
                    false,
                    "User already registered",
                    null
            );
        }

        Account acc = new Account();
        acc.setName(dto.getName());
        acc.setMobile(dto.getMobile());
        acc.setEmail(dto.getEmail());
        acc.setPassword(dto.getPassword());
        acc.setAddress(dto.getAddress());
        
        acc.setRole(Role.USER);

        Account saved = accountRepository.save(acc);

        return new AuthResponseDTO(
                true,
                "User registered successfully",
                saved
        );
    }

    // 2️⃣ SEND OTP (DUMMY)
    public AuthResponseDTO sendOtp(String mobile) {

        Account user = accountRepository.findByMobile(mobile)
                .orElse(null);

        if (user == null || user.getRole() != Role.USER) {
            return new AuthResponseDTO(
                    false,
                    "Mobile number not registered",
                    null
            );
        }

        return new AuthResponseDTO(
                true,
                "OTP sent successfully (Dummy OTP: 123456)",
                null
        );
    }

    // 3️⃣ VERIFY OTP & LOGIN USER
    public AuthResponseDTO verifyOtp(String mobile, String otp) {

        if (!DUMMY_OTP.equals(otp)) {
            return new AuthResponseDTO(
                    false,
                    "Invalid OTP",
                    null
            );
        }

        Account user = accountRepository.findByMobile(mobile)
                .orElse(null);

        if (user == null || user.getRole() != Role.USER) {
            return new AuthResponseDTO(
                    false,
                    "User not found",
                    null
            );
        }

        return new AuthResponseDTO(
                true,
                "Login successful",
                user
        );
    }

    // 4️⃣ ADMIN LOGIN
    public AuthResponseDTO adminLogin(AdminLoginDTO dto) {

        Account admin = accountRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!admin.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Not an admin account");
        }

        return new AuthResponseDTO(
                true,
                "Admin login successful",
                admin
        );
    }
}
