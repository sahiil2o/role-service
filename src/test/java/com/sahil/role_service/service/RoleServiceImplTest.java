package com.sahil.role_service.service;


import com.sahil.role_service.dto.RoleRequest;
import com.sahil.role_service.dto.RoleResponse;
import com.sahil.role_service.exception.DuplicateRoleNameException;
import com.sahil.role_service.exception.ResourceNotFoundException;
import com.sahil.role_service.model.Role;
import com.sahil.role_service.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest
{

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleRequest request;
    private Role role;

    @BeforeEach
    public void setUp()
    {
        request = new RoleRequest();
        request.setName("ADMIN_ROLE");
        request.setDescription("Administrator Role");

        role = new Role();
        role.setId(1L);
        role.setName("ADMIN_ROLE");
        role.setDescription("Administrator Role");

    }

    @Test
    void createRole_success()
    {
        when(roleRepository.existsByName(request.getName())).thenReturn(false);
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        RoleResponse response = roleService.createRole(request);

        assertNotNull(response);
        assertEquals("ADMIN_ROLE",response.getName());
        assertEquals("Administrator Role",response.getDescription());
        verify(roleRepository,times(1)).save(any(Role.class));
    }

    @Test
    void createRole_duplicateName_throwsException()
    {
        when(roleRepository.existsByName(request.getName())).thenReturn(true);

        assertThrows(DuplicateRoleNameException.class,
                () -> roleService.createRole(request));
        verify(roleRepository,never()).save(any());
    }
    @Test
    void getRoleById_success()
    {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        RoleResponse response = roleService.getRoleById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("ADMIN_ROLE",response.getName());
        assertEquals("Administrator Role",response.getDescription());
    }
    @Test
    void getRoleById_notFound_throwsException()
    {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> roleService.getRoleById(99L));
    }
    @Test
    void getRoleByName_success()
    {
        when(roleRepository.findByName("ADMIN_ROLE")).thenReturn(Optional.of(role));

        RoleResponse response = roleService.getRoleByName("ADMIN_ROLE");

        assertNotNull(response);
        assertEquals("ADMIN_ROLE",response.getName());
    }

    @Test
    void getRoleByName_notFound_throwsException()
    {
        when(roleRepository.findByName("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> roleService.getRoleByName("UNKNOWN"));
    }
    @Test
    void getAllRoles_success()
    {
        List<Role> roles = List.of(role);
        when(roleRepository.findAll()).thenReturn(roles);

        List<RoleResponse> response = roleService.getAllRoles();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("ADMIN_ROLE", response.get(0).getName());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void updateRole_success()
    {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        RoleResponse response = roleService.updateRole(1L, request);

        assertNotNull(response);
        assertEquals("ADMIN_ROLE",response.getName());
        verify(roleRepository,times(1)).save(any(Role.class));
    }
    @Test
    void updateRole_duplicateName_throwsException()
    {
        Role existing = new Role();
        existing.setId(1L);
        existing.setName("OLD_ROLE");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(roleRepository.existsByName(request.getName())).thenReturn(true);
        assertThrows(DuplicateRoleNameException.class,
                () -> roleService.updateRole(1L, request));
        verify(roleRepository,never()).save(any());
    }
    @Test
    void deleteRoleById_success()
    {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        roleService.deleteRoleById(1L);
        verify(roleRepository,times(1)).delete(role);
    }

    @Test
    void deleteRoleById_notFound_throwsException()
    {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> roleService.deleteRoleById(99L));
        verify(roleRepository,never()).delete(any());
    }
}
