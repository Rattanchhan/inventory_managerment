package com.inventory_managerment.feature.user;
import com.inventory_managerment.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User AS u JOIN FETCH u.role AS r JOIN FETCH r.rolePermissions AS rp JOIN FETCH rp.permission")
    Page<User> getAllByPage(PageRequest pageRequest);

    @Query(value = "SELECT u FROM User AS u JOIN FETCH u.role AS r JOIN FETCH r.rolePermissions AS rp JOIN FETCH rp.permission WHERE u.id= :id")
    Optional<User> getUserById(@Param("id")Long id);
}
