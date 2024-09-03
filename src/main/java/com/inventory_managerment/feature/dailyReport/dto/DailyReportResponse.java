package com.inventory_managerment.feature.dailyReport.dto;
import java.util.Date;
import java.math.*;
import jakarta.persistence.Basic;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public record DailyReportResponse(

    Long id,
    Date issuedAt,
    BigDecimal totalSell,
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt,
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt
) {
    
}
