package com.kb.company.controller;

import com.kb.company.dto.Company;
import com.kb.company.dto.RequestCompany;
import com.kb.company.service.CompanyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "CompanyController", tags = "회사 공고 정보")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService service;

    @GetMapping("")
    public ResponseEntity<List<Company>> getAllNotice() {
        List<Company> notices = service.getAllNotice();

        if (notices.size() == 0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notices);
    }

    @PostMapping("")
    public ResponseEntity<Company> createCompany(@RequestBody RequestCompany requestCompany) {
        Company company = service.createCompany(requestCompany);

        if (company == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(company);
    }

    @GetMapping("/notice")
    public ResponseEntity<List<Company>> getRecommendedCompanyList(@RequestParam("cno") int cno) {
        List<Company> companyList = service.getRecommendedCompanyList(cno);

        if (companyList == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(companyList);
    }
}
