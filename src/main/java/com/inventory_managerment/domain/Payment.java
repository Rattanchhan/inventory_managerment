package com.inventory_managerment.domain;
import com.inventory_managerment.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.*;;

@Entity
@Table(name="payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Positive
    private BigDecimal totalAmount;
    @Column(nullable = false)
    @Positive
    private BigDecimal totalDiscount;
    @Column(nullable = false)
    @Positive
    private BigDecimal paymentAmount;

    private BigDecimal paidAmount;

    private BigDecimal ownAmount;

    private String paymentMethod;
    private String status;
    private Integer createdBy;

    private Boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
}
