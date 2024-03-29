package com.faceit.faceit.controller;
import com.faceit.faceit.model.entity.Player;
import com.faceit.faceit.model.entity.User;
import com.faceit.faceit.security.JwtCore;
import com.faceit.faceit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JwtCore jwtCore;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
        return  ResponseEntity.ok("User was successfully deleted");
    }
    @GetMapping("/getUsersByPlayer")
    public ResponseEntity<List<User>> getUsersByPlayer(@RequestParam String nickname) {
        return new ResponseEntity<>(userService.getUsersByPlayer(nickname), HttpStatus.OK);
    }
    @GetMapping("/getUserByName")
    public ResponseEntity<User> getUsersByName(@RequestParam String username) {
        return new ResponseEntity<>(userService.getUserByName(username), HttpStatus.OK);
    }

    @PostMapping("/addPlayer")
    public ResponseEntity<String> addPlayerToUser(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String nickname) {
        String token = userService.getTokenFromRequest(authorizationHeader);
        String username = jwtCore.getNameFromJwt(token);
        return  ResponseEntity.ok(userService.addPlayerToUser(username, nickname));
    }
    @GetMapping("/getPlayers")
    public ResponseEntity<Set<Player>> getAllPlayers(@RequestHeader("Authorization") String authorizationHeader) {
        String token = userService.getTokenFromRequest(authorizationHeader);
        String username = jwtCore.getNameFromJwt(token);
        return ResponseEntity.ok(userService.getFavoritePlayersByUsername(username));
    }
    @DeleteMapping("/deletePlayer")
    public ResponseEntity<String> deletePlayer(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String nickname) {
        String token = userService.getTokenFromRequest(authorizationHeader);
        String username = jwtCore.getNameFromJwt(token);
        userService.removePlayer(username,nickname);
        return ResponseEntity.ok("Player was successfully deleted");
    }


}