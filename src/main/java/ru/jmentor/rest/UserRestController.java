package ru.jmentor.rest;

import org.springframework.web.bind.annotation.*;
import ru.jmentor.dto.UserDto;
import ru.jmentor.model.User;
import ru.jmentor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/user/{id}")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){ return new ResponseEntity<>(HttpStatus.NO_CONTENT); }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
