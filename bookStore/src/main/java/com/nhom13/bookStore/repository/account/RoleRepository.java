package com.nhom13.bookStore.repository.account;

import com.nhom13.bookStore.model.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
