package com.vannarith.bussiness.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vannarith.bussiness.feature.token.TokenRepository;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public void resetPassword(ResetPasswordRequest request) {

        Optional<User> opUser = repository.findByEmail(request.getEmail());
        if(!opUser.isPresent()){
            throw new IllegalStateException("User not Found");
        }
        
        // update the password
        opUser.get().setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(opUser.get());
    }

    public void changeRole(ChangeRoleRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // // update the password
        user.setRole(Role.ADMIN);

        // save the new password
        repository.save(user);
    }

    public void deleteUser(DeleteUserRequest request) {

        Optional<User> opUser = repository.findByEmail(request.getEmail());
        if(!opUser.isPresent()){
            throw new IllegalStateException("User not Found");
        }
        tokenRepository.deleteByUserId(opUser.get().getId());
        repository.deleteById(opUser.get().getId());

    }
}
