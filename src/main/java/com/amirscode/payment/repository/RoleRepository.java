package com.amirscode.payment.repository;

import com.amirscode.payment.entity.Role;
import com.amirscode.payment.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleEnum roleName);

}
