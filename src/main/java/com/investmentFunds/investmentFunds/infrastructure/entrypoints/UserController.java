package com.investmentFunds.investmentFunds.infrastructure.entrypoints;

import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.usecase.UserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    public ResponseEntity<User> createdUser(@RequestBody User user){
        return new ResponseEntity<>(userUseCase.saveUser(user), HttpStatus.CREATED);
    }
}
