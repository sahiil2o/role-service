package com.sahil.role_service.controller;


import com.sahil.role_service.dto.RoleRequest;
import com.sahil.role_service.dto.RoleResponse;
import com.sahil.role_service.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    @Autowired
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request){
        return ResponseEntity.ok().body(roleService.createRole(request));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(roleService.getRoleById(id));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<RoleResponse> getByName(@PathVariable String name){
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest request){
        return ResponseEntity.ok(roleService.updateRole(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id){
        roleService.deleteRoleById(id);
        return ResponseEntity.noContent().build();
    }
}
