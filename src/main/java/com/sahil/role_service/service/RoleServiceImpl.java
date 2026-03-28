package com.sahil.role_service.service;

import com.sahil.role_service.dto.RoleRequest;
import com.sahil.role_service.dto.RoleResponse;
import com.sahil.role_service.exception.DuplicateRoleNameException;
import com.sahil.role_service.exception.ResourceNotFoundException;
import com.sahil.role_service.model.Role;
import com.sahil.role_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest request){
        if(roleRepository.existsByName(request.getName())){
            throw new DuplicateRoleNameException("Role already exists: "+request.getName());
        }
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        Role saved = roleRepository.save(role);
        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles(){
        return roleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Role Not found: " + id));
        return mapToDTO(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleByName(String name){
        Role role = roleRepository.findByName(name)
                .orElseThrow(()-> new ResourceNotFoundException("Role Not found: " + name));
        return mapToDTO(role);
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Long id,RoleRequest request){

        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Role Not found: " + id));
        if(!role.getName().equals(request.getName())
                && roleRepository.existsByName(request.getName())){
            throw new DuplicateRoleNameException("Role already exists: "+request.getName());
        }
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        Role updated = roleRepository.save(role);
        return mapToDTO(updated);
    }

    @Override
    @Transactional
    public void deleteRoleById(Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Role Not found: " + id));
        roleRepository.delete(role);
    }

    private RoleResponse mapToDTO(Role role){
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        return roleResponse;
    }
}
