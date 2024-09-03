package com.inventory_managerment.mapper.dailyReport;
import com.inventory_managerment.domain.DailyReport;
import com.inventory_managerment.feature.dailyReport.dto.DailyReportResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailyReportMapper {
    
    DailyReportResponse fromReportResponse(DailyReport dailyReport);
}
