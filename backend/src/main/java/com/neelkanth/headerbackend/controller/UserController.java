package com.neelkanth.headerbackend.controller;

import com.neelkanth.headerbackend.dto.ImportantDateDTO;
import com.neelkanth.headerbackend.dto.UserDTO;
import com.neelkanth.headerbackend.entity.User;
import com.neelkanth.headerbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    /*public ResponseEntity createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.saveUser(user));
    }*/
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        UserDTO userDTO = mapToDTO(savedUser);
        return ResponseEntity.status(201).body(userDTO);
    }

    @GetMapping
    public ResponseEntity getUsers(){
        List<User> users = userService.fetchUsers();
        List<UserDTO> userDTOS = users.stream().map(this::mapToDTO).collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.fetchUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setMiddleName(user.getMiddleName());
        dto.setLastName(user.getLastName());
        dto.setDob(user.getDob());
        dto.setAddr1(user.getAddr1());
        dto.setAddr2(user.getAddr2());
        dto.setAddr3(user.getAddr3());
        dto.setProfession(user.getProfession());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhoneNos(user.getPhoneNos());
        dto.setImportantDates(user.getImportantDates().stream()
                .map(date -> {
                    ImportantDateDTO dateDTO = new ImportantDateDTO();
                    dateDTO.setId(date.getId());
                    dateDTO.setOccasion(date.getOccasion());
                    dateDTO.setDate(date.getDate());
                    dateDTO.setRestricted(date.getRestricted());
                    return dateDTO;
                })
                .toList());
        return dto;
    }
}
