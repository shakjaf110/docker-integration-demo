package com.example.demo.repo

import com.example.demo.domain.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KotlinTestRepository:JpaRepository<Test, Int>  {

}