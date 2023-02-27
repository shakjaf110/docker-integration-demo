package com.example.demo.controller

import com.example.demo.domain.Test
import com.example.demo.service.KotlinTestService
import com.example.demo.service.TestService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/kotlin")
@PreAuthorize("hasAuthority('admin')")
open class KotlinTestController(
    val kotlinTestService: KotlinTestService,
    val testService: TestService
)  {

    @GetMapping("/test/all")
    open fun getCall():ResponseEntity<List<Test>> {
        val tests=testService?.findAll()
        val testResponseEntity= ResponseEntity<List<Test>>(tests,HttpStatus.FOUND);
        return testResponseEntity;
    }

    @GetMapping("/test/{id}")
    fun getCall(@PathVariable id:Int):ResponseEntity<Test> {
        var test=kotlinTestService?.findById(id)?.get()
        var testResponseEntity= ResponseEntity<Test>(test,HttpStatus.FOUND);
        return testResponseEntity;
    }

}