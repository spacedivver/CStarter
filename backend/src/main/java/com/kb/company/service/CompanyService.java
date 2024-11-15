package com.kb.company.service;

import com.kb.company.dto.Company;
import com.kb.company.dto.RequestCompany;
import com.kb.company.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyMapper mapper;

    public List<Company> getAllNotice() {
        return mapper.selectAllNotice();
    }

    public Company findCompany(int cpno) {
        return mapper.selectCompany(cpno);
    }

    public Company createCompany(RequestCompany requestCompany) {
        Company company = Company.builder()
                .name(requestCompany.getName())
                .title(requestCompany.getTitle())
                .isUserOption(requestCompany.getIsUserOption())
                .build();

        if (mapper.insertCompany(company) == 0) {
            return null;
        }

        System.out.println(company.getCpno());

        return mapper.selectCompany(company.getCpno());
    }
}
