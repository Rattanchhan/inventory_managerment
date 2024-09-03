package com.inventory_managerment.feature.dailyReport;
import java.time.LocalDate;
import java.util.Date;
import com.inventory_managerment.feature.dailyReport.dto.DailyReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dailyReports")
@RequiredArgsConstructor
public class DailyReportController {
    private final DailyReportServices dailyReportServices;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public DailyReportResponse generateReport(@RequestParam("date") String time){
        return dailyReportServices.createReport(LocalDate.parse(time));
    }

    @GetMapping("/getAll")
    public Page<DailyReportResponse> getReports(@RequestParam("pageNumber") String pageNumber,@RequestParam("pageSize") String pageSize){
        return dailyReportServices.getAll(Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
    }
    
    @GetMapping("/{id}/find")
    public DailyReportResponse getReport(@PathVariable("id") String id){
        return dailyReportServices.getById(Long.parseLong(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteReport(@PathVariable("id") String id){
        return dailyReportServices.deleteReport(Long.parseLong(id));
    }
}
