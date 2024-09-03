package com.inventory_managerment.feature.dailyReport;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import com.inventory_managerment.domain.DailyReport;
import com.inventory_managerment.feature.dailyReport.dto.DailyReportResponse;
import com.inventory_managerment.mapper.dailyReport.DailyReportMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyReportServicesImplementation implements DailyReportServices{

    private final DailyReportRepository dailyReportRepository;
    private final DailyReportMapper dailyReportMapper;
    
    @Override
    public DailyReportResponse createReport(LocalDate date) {
        DailyReport dailyReport  = new DailyReport();
        dailyReport.setIssuedAt(LocalDate.now());
        dailyReport.setCreatedAt(new Date());
        dailyReport.setUpdatedAt(new Date());
        dailyReport.setTotalSell(dailyReportRepository.getTotalSell(date));

        dailyReport=dailyReportRepository.save(dailyReport);

        return dailyReportMapper.fromReportResponse(dailyReport);   
    }

    @Override
    public Page<DailyReportResponse> getAll(int pageNumber,int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,Direction.DESC,"totalSell");
        Page<DailyReport> dailyReports = dailyReportRepository.findAll(pageRequest);
        return dailyReports.map(dailyReportMapper::fromReportResponse);
    }

    @Override
    public DailyReportResponse getById(long id) {
        return dailyReportMapper.fromReportResponse(dailyReportRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Report has not been found...")
        ));
    }

    @Override
    public ResponseEntity<?> deleteReport(long id) {
         if(dailyReportRepository.existsById(id)){
            dailyReportRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Deleted",Boolean.TRUE));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Report has not been found");
    }
    
}
