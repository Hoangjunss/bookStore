package com.nhom13.bookStore.repository.account;

import com.nhom13.bookStore.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
