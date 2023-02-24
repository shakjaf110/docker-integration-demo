package com.example.demo.controller

import com.example.demo.dto.UserDto
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {

    @PostMapping("/users")
    fun save(@RequestBody userDto: UserDto): UserDto{
        return userService.save(userDto)
    }

}