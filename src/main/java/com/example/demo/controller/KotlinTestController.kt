package com.example.demo.controller

import com.example.demo.domain.Test
import com.example.demo.repo.KotlinTestRepository
import com.example.demo.repo.TestRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/kotlin")
class KotlinTestController {

    @Autowired
    var testRepo: TestRepo? = null

    @Autowired
    var kotlinTestRepository: KotlinTestRepository? = null

    @GetMapping("/test/all")
    fun getCall():ResponseEntity<List<Test>> {
        val tests=testRepo?.findAll()
        val testResponseEntity= ResponseEntity<List<Test>>(tests,HttpStatus.FOUND);
        return testResponseEntity;
    }

    @GetMapping("/test/{id}")
    fun getCall(@PathVariable id:Int):ResponseEntity<Test> {
        var test=kotlinTestRepository?.findById(id)?.get()
        var testResponseEntity= ResponseEntity<Test>(test,HttpStatus.FOUND);
        return testResponseEntity;
    }

}