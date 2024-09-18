package com.inventory_managerment.domain;
import com.inventory_managerment.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,name = "username")
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private BigDecimal salary;
    @Column(nullable = false)
    private LocalDate date_of_birth;
    private String status;
    private String remark;

    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User user;

    @OneToMany(mappedBy = "user")
    private List<User> users;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
}
