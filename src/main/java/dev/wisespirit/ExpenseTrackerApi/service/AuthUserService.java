package dev.wisespirit.ExpenseTrackerApi.service;

import dev.wisespirit.ExpenseTrackerApi.dto.AuthUserCreateDto;
import dev.wisespirit.ExpenseTrackerApi.dto.AuthUserDto;
import dev.wisespirit.ExpenseTrackerApi.dto.AuthUserUpdateDto;
import dev.wisespirit.ExpenseTrackerApi.model.AuthUser;
import dev.wisespirit.ExpenseTrackerApi.repository.AuthUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthUserService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AuthUserDto> createUser(AuthUserCreateDto dto) {
        AuthUser authUser = new AuthUser();
        authUser.setName(dto.name());
        authUser.setUsername(dto.username());
        authUser.setAuthRole(dto.authRole());
        authUser.setPassword(passwordEncoder.encode(dto.password()));
        AuthUser savedUser = authUserRepository.save(authUser);
        return Optional.of(convertEntityToDto(savedUser));
    }

    public Optional<AuthUserDto> updateUser(AuthUserUpdateDto dto, Long id) {
        return authUserRepository.findById(id)
                .map(authUser -> {
                    if (dto.name() != null) {
                        authUser.setName(dto.name());
                    }
                    if (dto.username() != null) {
                        authUser.setUsername(dto.username());
                    }
                    if (dto.authRole() != null) {
                        authUser.setAuthRole(dto.authRole());
                    }
                    authUserRepository.save(authUser);
                    return convertEntityToDto(authUser);
                });
    }

    public void deleteUser(Long id) {
        authUserRepository.deleteById(id);
    }

    public Optional<AuthUserDto> getUserById(Long id) {
        return authUserRepository.findById(id)
                .map(AuthUserService::convertEntityToDto);
    }

    public boolean existById(Long id) {
        return authUserRepository.existsById(id);
    }

    public static AuthUserDto convertEntityToDto(AuthUser authUser) {
        return new AuthUserDto(authUser.getName(),
                authUser.getUsername(),
                authUser.getAuthRole(),
                authUser.getTelegramId());
    }


}
