package com.inventory_managerment.feature.role.dto;

import com.inventory_managerment.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT r FROM Role As r WHERE r.name='Administrator'")
    Role findRoleUser();

}