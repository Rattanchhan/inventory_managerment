package com.inventory_managerment.domain;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import com.inventory_managerment.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "daily_reports")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailyReport extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalSell;
    private LocalDate issuedAt;
}
