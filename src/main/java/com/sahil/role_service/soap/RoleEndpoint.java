package com.sahil.role_service.soap;

import com.sahil.role_service.dto.RoleResponse;
import com.sahil.role_service.service.RoleService;
import com.sahil.role_service.ws.GetRoleByNameRequest;
import com.sahil.role_service.ws.GetRoleByNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class RoleEndpoint {
    private static final String NAMESPACE_URI = "http://sahil.com/role-service/ws";
    private final RoleService roleService;

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "GetRoleByNameRequest")
    @ResponsePayload
    public GetRoleByNameResponse getRoleByName(@RequestPayload GetRoleByNameRequest request) {
        RoleResponse role = roleService.getRoleByName(request.getName());

        GetRoleByNameResponse response = new GetRoleByNameResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        return response;
    }
}
