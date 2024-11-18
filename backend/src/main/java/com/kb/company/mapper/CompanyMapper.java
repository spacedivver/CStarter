package com.kb.company.mapper;

import com.kb.company.dto.company.Company;
import com.kb.company.dto.job.CoverLetterItem;
import com.kb.company.dto.job.Job;

import java.util.List;

public interface CompanyMapper {
    List<Company> selectAllNotice();
    Company selectCompany(int cpno);
    int insertCompany(Company company);
    List<Integer> selectRecommendedCompany(int cno);
    List<Job> selectJobByCompany(int cpno);
    List<CoverLetterItem> selectCoverLetterItem(int jno);
}
