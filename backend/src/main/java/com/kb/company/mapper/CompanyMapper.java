package com.kb.company.mapper;

import com.kb.company.dto.Company;

import java.util.List;

public interface CompanyMapper {
    List<Company> selectAllNotice();
    Company selectCompany(int cpno);
    int insertCompany(Company company);
}
