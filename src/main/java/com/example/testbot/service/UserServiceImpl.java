package com.example.testbot.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import com.example.testbot.model.Address;
import com.example.testbot.model.request.CreateAddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.testbot.model.User;
import com.example.testbot.model.request.CreateUserRequest;
import com.example.testbot.model.response.AddressResponse;
import com.example.testbot.model.response.UserResponse;
import com.example.testbot.repository.UserRepository;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::buildUserResponse)
                .collect(Collectors.toList());
    }

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(@NotNull Integer userId) {

        return userRepository.findById(userId)
                .map(this::buildUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " is not found"));
    }

    @NotNull
    @Override
    @Transactional
    public UserResponse createUser(@NotNull CreateUserRequest request) {
        User user = buildUserRequest(request);
        return buildUserResponse(userRepository.save(user));
    }

    @NotNull
    @Override
    @Transactional
    public UserResponse update(@NotNull Integer userId,@NotNull CreateUserRequest request) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " is not found"));
        userUpdate(user, request);
        return buildUserResponse(userRepository.save(user));
    }

    @NotNull
    @Override
    @Transactional
    public void delete(@NotNull Integer userId) {
        userRepository.deleteById(userId);
    }


    private void userUpdate(@NotNull User user, @NotNull CreateUserRequest request) {
        ofNullable(request.getLogin()).map(user::setLogin);
        ofNullable(request.getFirstName()).map(user::setFirstName);
        ofNullable(request.getMiddleName()).map(user::setMiddleName);
        ofNullable(request.getLastName()).map(user::setLastName);
        ofNullable(request.getAge()).map(user::setAge);

        CreateAddressRequest addressRequest = request.getAddress();
        if (addressRequest != null) {
            ofNullable(addressRequest.getBuilding()).map(user.getAddress()::setBuilding);
            ofNullable(addressRequest.getStreet()).map(user.getAddress()::setStreet);
            ofNullable(addressRequest.getCity()).map(user.getAddress()::setCity);
        }
    }

    @NotNull
    private User buildUserRequest(@NotNull CreateUserRequest request) {
        return new User()
                .setLogin(request.getLogin())
                .setAge(request.getAge())
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAddress(new Address()
                        .setCity(request.getAddress().getCity())
                        .setBuilding(request.getAddress().getBuilding())
                        .setStreet(request.getAddress().getStreet()));
    }

    @NotNull
    private UserResponse buildUserResponse(@NotNull User user) {
        return new UserResponse()
                .setId(user.getId())
                .setLogin(user.getLogin())
                .setAge(user.getAge())
                .setFirstName(user.getFirstName())
                .setMiddleName(user.getMiddleName())
                .setLastName(user.getLastName())
                .setAddress(new AddressResponse()
                        .setCity(user.getAddress().getCity())
                        .setBuilding(user.getAddress().getBuilding())
                        .setStreet(user.getAddress().getStreet()));
    }



}
