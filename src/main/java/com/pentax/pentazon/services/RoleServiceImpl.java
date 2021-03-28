package com.pentax.pentazon.services;

import com.pentax.pentazon.exceptions.UserRoleNotFoundException;
import com.pentax.pentazon.models.Role;
import com.pentax.pentazon.repository.RoleRepository;
import com.pentax.pentazon.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(String name) throws UserRoleNotFoundException {
        return findRoleByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return getAllRolesInDb();
    }
    private List<Role> getAllRolesInDb(){
       return roleRepository.findAll();
    }

    private Role findRoleByName(String name) throws UserRoleNotFoundException{
        Optional<Role> roleOptional = roleRepository.findRoleByName(name);
        if(roleOptional.isPresent()){
            return roleOptional.get();
        } else {
            throw new UserRoleNotFoundException();
        }
    }

    @Override
    public void createNewRole(Role role) {
        createANewRole(role);
    }

    private void createANewRole(Role role){
        roleRepository.save(role);
    }
}
