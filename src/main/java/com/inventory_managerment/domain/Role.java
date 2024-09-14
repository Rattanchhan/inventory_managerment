package com.inventory_managerment.domain;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;

import com.inventory_managerment.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<Role_Permission> rolePermissions = new HashSet<>();

    @Override
    public String getAuthority() {
        return "ROLE_"+name;
    }
}
