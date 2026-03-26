package com.sahil.role_service.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GetRoleByNameResponse",namespace ="http://sahil.com/role-service/ws")
public class GetRoleByNameResponse {
    @XmlElement(required = true,namespace = "http://sahil.com/role-service/ws")
    private Long id;
    @XmlElement(required = true,namespace = "http://sahil.com/role-service/ws")
    private String name;
    @XmlElement(namespace = "http://sahil.com/role-service/ws")
    private String description;
}
