package com.gtxc.patikacloneserver.service;

/*
    Created by gt at 12:10 PM on Tuesday, March 08, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.service.
*/

import com.gtxc.patikacloneserver.exceptions.EntityNotFoundException;
import com.gtxc.patikacloneserver.model.Role;
import com.gtxc.patikacloneserver.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements SimpleEntityService<Role, Long> {

    public static final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Could not found role " + id
                ));
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            throw new EntityNotFoundException("Could not found any role");
        }
        return roles;
    }

    @Override
    public Role addNew(Role role) {
        if (role.getId() != null && roleRepository.existsById(role.getId())) {
            String unavailable = "Role id '" + role.getId() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        } else if (role.getRoleType() != null && roleRepository.existsByRoleType(role.getRoleType())) {
            String unavailable = "Role Type '" + role.getRoleType() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        }
        Role newRole = roleRepository.save(role);
        if (newRole.getId() != null) {
            return newRole;
        } else {
            String retrieveError = "Error while getting added user : " + role;
            log.warn(retrieveError);
            throw new EntityNotFoundException(retrieveError);
        }
    }

    @Override
    public void removeById(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        }
    }

    @Override
    public void removeAll() {
        if (!roleRepository.findAll().isEmpty()) {
            roleRepository.deleteAll();
        }
    }

    @Override
    public Role update(Role role, Long id) {
        Role oldRole = getById(id);
        oldRole.setId(id);
        oldRole.setRoleType(role.getRoleType());
        removeById(id);
        return addNew(oldRole);
    }
}
