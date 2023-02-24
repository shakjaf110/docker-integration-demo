package com.example.demo.service

import com.example.demo.domain.Test
import com.example.demo.repo.TestRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class KotlinTestService(val testRepository: TestRepository) {

    fun findAll(): List<*>? {
        return testRepository.findAll()
    }

    fun findById(id: Int): Optional<Test?>? {
        return testRepository.findById(id)
    }
}