package com.soen343.shs.dal.controllers;

import com.soen343.shs.dal.service.UserService;
import com.soen343.shs.dto.RealUserDTO;
import com.soen343.shs.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping(path = "/user/update}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    UserDTO updateUserLocation(@AuthenticationPrincipal @RequestBody final Authentication authentication, final RealUserDTO user) {
        return userService.updateUser(user);
    }

    @GetMapping(value = "/user/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public RealUserDTO getUser(@PathVariable final String username) {
        return userService.getUserByUsername(username, RealUserDTO.class);
    }


    @GetMapping(value = "/logout")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String logoutUser() {
        return "USER SUCCESSFULLY LOGGED OUT AND CLOSED THE SIMULATION";
    }

    @GetMapping(value = "/user")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public RealUserDTO getAuthenticatedUser(@AuthenticationPrincipal final Authentication authentication) {
        return userService.getUserByUsername(authentication.getName(), RealUserDTO.class);
    }

    @PutMapping(value = "/outside")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public RealUserDTO moveUserOutside(@AuthenticationPrincipal final Authentication auth) {
        return userService.leaveHouse(auth.getName());
    }

}
