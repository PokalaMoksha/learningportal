package com.learning.employee.controller;

import com.learning.employee.service.EmployeeService;
import com.learning.common.annotation.CheckHierarchy;
import com.learning.common.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // ── Employee APIs ─────────────────────────

    @CheckHierarchy
    @GetMapping("/my-details")
    public ResponseEntity<EmployeeResponse>
           getMyDetails(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   employeeService
                       .getMyDetails(username),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/my-learning")
    public ResponseEntity<List<LearningResponse>>
           getMyLearning(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   employeeService
                       .getMyLearning(username),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/my-manager")
    public ResponseEntity<ManagerResponse>
           getMyManager(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   employeeService
                       .getMyManager(username),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/my-certificates")
    public ResponseEntity<List<CertificateResponse>>
           getMyCertificates(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   employeeService
                       .getMyCertificates(username),
                   HttpStatus.OK);
    }

    // ── Feign APIs ────────────────────────────

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponse>>
           getAllEmployees() {
        return new ResponseEntity<>(
                   employeeService.getAllEmployees(),
                   HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse>
           getEmployeeById(
           @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                   employeeService
                       .getEmployeeById(id),
                   HttpStatus.OK);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<EmployeeResponse>>
           getEmployeesByManager(
           @PathVariable("managerId") Long managerId) {
        return new ResponseEntity<>(
                   employeeService
                       .getEmployeesByManager(
                        managerId),
                   HttpStatus.OK);
    }

    @GetMapping("/exists/{id}/manager/{managerId}")
    public ResponseEntity<Boolean>
           existsByIdAndManagerId(
           @PathVariable("id") Long id,
           @PathVariable("managerId") Long managerId) {
        return new ResponseEntity<>(
                   employeeService
                       .existsByIdAndManagerId(
                        id, managerId),
                   HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeResponse>
           addEmployee(
           @RequestBody UserRequest request) {
        return new ResponseEntity<>(
                   employeeService
                       .addEmployee(request),
                   HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponse>
           updateEmployee(
           @PathVariable("id") Long id,
           @RequestBody UserRequest request) {
        return new ResponseEntity<>(
                   employeeService
                       .updateEmployee(id, request),
                   HttpStatus.OK);
    }
}