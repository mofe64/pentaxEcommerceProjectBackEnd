package com.pentax.pentazon.services;

import com.pentax.pentazon.exceptions.UserRoleNotFoundException;
import com.pentax.pentazon.models.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    Role findByName(String name) throws UserRoleNotFoundException;
    List<Role> getAllRoles();
    void createNewRole(Role role);
}
