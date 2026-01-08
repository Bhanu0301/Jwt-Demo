package com.example.web.controller;

import com.example.web.entity.AuthRequest;
import com.example.web.entity.UserInfo;
import com.example.web.service.JwtService;
import com.example.web.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {

    private UserInfoService service;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest){
        //Triggers Spring security authentication pipeline
        Authentication auth = authenticationManager.authenticate(
                //Authentication object -> Credentials Container
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        //If authenticated generate JWT token
        if(auth.isAuthenticated()){
            //return signed token
            return jwtService.generateToken(authRequest.getUsername());
        }
        else{
            throw  new UsernameNotFoundException("Invalid user Request!");
        }
    }
}


