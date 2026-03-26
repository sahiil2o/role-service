package com.sahil.role_service.service;

import com.sahil.role_service.dto.RoleRequest;
import com.sahil.role_service.dto.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
    List<RoleResponse> getAllRoles();
    RoleResponse getRoleById(Long id);
    RoleResponse getRoleByName(String name);
    RoleResponse updateRole(Long id,RoleRequest request);
    void deleteRoleById(Long id);
}
