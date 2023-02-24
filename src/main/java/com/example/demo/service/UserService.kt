package com.example.demo.service

import com.example.demo.domain.Users
import com.example.demo.dto.UserDto
import com.example.demo.repo.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
open class UserService(private val passwordEncoder: BCryptPasswordEncoder, val userRepository: UserRepository) {

    fun save(userDto: UserDto): UserDto{
        var user: Users = Users();
        user.name=userDto.name
        user.email=userDto.email
        user.password=passwordEncoder.encode(userDto.password)
        user=userRepository.save(user)
        userDto.id=user.id;
        return  userDto;
    }

}