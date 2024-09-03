package com.inventory_managerment.feature.dailyReport;
import java.time.LocalDate;
import java.util.Date;

import com.inventory_managerment.feature.dailyReport.dto.DailyReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface DailyReportServices {

    DailyReportResponse createReport(LocalDate time);

    Page<DailyReportResponse> getAll(int i, int j);

    DailyReportResponse getById(long l);

    ResponseEntity<?> deleteReport(long long1);
    
}
