package com.example.demo.service

import com.example.demo.domain.UserSecurity
import com.example.demo.repo.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserDetailsService(
    private val repository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
       // Create a method in your repo to find a user by its username
        val user = repository.findByEmail(username) ?: throw UsernameNotFoundException("$username not found")
        return UserSecurity(
            user.id,
            user.email,
            user.password,
            Collections.singleton(SimpleGrantedAuthority(user.role))
        )
    }
}