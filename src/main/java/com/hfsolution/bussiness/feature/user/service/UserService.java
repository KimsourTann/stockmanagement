package com.hfsolution.bussiness.feature.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hfsolution.bussiness.app.dto.SearchRequestDTO;
import com.hfsolution.bussiness.app.exception.AppException;
import com.hfsolution.bussiness.app.services.SearchFilter;
import com.hfsolution.bussiness.feature.token.TokenRepository;
import com.hfsolution.bussiness.feature.user.Enum.Role;
import com.hfsolution.bussiness.feature.user.dto.ChangePasswordRequest;
import com.hfsolution.bussiness.feature.user.dto.ChangeRoleRequest;
import com.hfsolution.bussiness.feature.user.dto.DeleteUserRequest;
import com.hfsolution.bussiness.feature.user.dto.ResetPasswordRequest;
import com.hfsolution.bussiness.feature.user.entity.User;
import com.hfsolution.bussiness.feature.user.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final SearchFilter<User> searchFilter;


    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            // throw new IllegalStateException("Wrong password");
            throw new AppException("003");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            // throw new IllegalStateException("Password are not the same");
            throw new AppException("004");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public void resetPassword(ResetPasswordRequest request) {

        Optional<User> opUser = repository.findByEmail(request.getEmail());
        if(!opUser.isPresent()){
            throw new AppException("002");
            // throw new IllegalStateException("User not Found");
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

    public User getInfo(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return user;
    }

    public List<User> getUsersInfo(SearchRequestDTO request) {
        Specification<User> users = searchFilter.getSearchSpecification(request.getSearchRequest(), request.getGlobalOperator());
        return repository.findAll(users);
    }

    public void deleteUser(DeleteUserRequest request) {

        Optional<User> opUser = repository.findByEmail(request.getEmail());
        if(!opUser.isPresent()){
            throw new AppException("002");
        }
        tokenRepository.deleteByUserId(opUser.get().getId());
        repository.deleteById(opUser.get().getId());

    }
}
