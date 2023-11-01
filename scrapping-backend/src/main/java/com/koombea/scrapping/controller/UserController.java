package com.koombea.scrapping.controller;

import com.koombea.scrapping.model.AppUser;
import com.koombea.scrapping.service.UserService;
import com.koombea.scrapping.utils.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody AppUser appUser, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AppUser createdAppUser = userService.registerUser(appUser);
        if(null != createdAppUser){
            return ResponseEntity
                    .created(uriBuilder.path("/user/{id}").buildAndExpand(createdAppUser.getId()).toUri())
                    .headers(headers)
                    .body(createdAppUser);
        }else{
            String errorMessage = "An error occurred while creating the user.";
            ErrorDetails.ErrorCode errorCode = ErrorDetails.ErrorCode.INTERNAL_ERROR; // You can define your custom error codes
            ErrorDetails errorDetails = new ErrorDetails(errorCode.getCode(), errorMessage);
            return new ResponseEntity<>(errorDetails, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody AppUser appUser) {
        AppUser user = userService.login(appUser);
        if(null != user){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);
        }else{
            String errorMessage = "An error occurred while getting user info.";
            ErrorDetails.ErrorCode errorCode = ErrorDetails.ErrorCode.UNAUTHORIZED; // You can define your custom error codes
            ErrorDetails errorDetails = new ErrorDetails(errorCode.getCode(), errorMessage);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(errorDetails, headers, HttpStatus.UNAUTHORIZED);
        }
    }
}

