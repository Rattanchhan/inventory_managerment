package com.inventory_managerment.feature.dailyReport;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.inventory_managerment.domain.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DailyReportRepository extends JpaRepository<DailyReport,Long> {

    @Query(value = "SELECT SUM(p.paymentAmount) FROM Payment as p where p.createdAt= :date ")
    BigDecimal getTotalSell(@Param("date") LocalDate date);
}
