package dev.wisespirit.ExpenseTrackerApi.controller;

import dev.wisespirit.ExpenseTrackerApi.dto.AuthUserCreateDto;
import dev.wisespirit.ExpenseTrackerApi.dto.AuthUserDto;
import dev.wisespirit.ExpenseTrackerApi.dto.AuthUserLogin;
import dev.wisespirit.ExpenseTrackerApi.dto.AuthUserUpdateDto;
import dev.wisespirit.ExpenseTrackerApi.service.AuthUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth_users")
public class UserController {
    private final AuthUserService authUserService;

    public UserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping()
    public ResponseEntity<AuthUserDto> createUser(@Valid @RequestBody AuthUserCreateDto dto){
        return authUserService.createUser(dto)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthUserLogin userLogin){
        if (authUserService.verifyUser(userLogin)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthUserDto> updateUser(@Valid @RequestBody AuthUserUpdateDto dto,
                                                  @PathVariable Long id){
        return authUserService.updateUser(dto, id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthUserDto> getUserById(@PathVariable Long id){
        return authUserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        if (authUserService.existById(id)) {
            authUserService.deleteUser(id);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
