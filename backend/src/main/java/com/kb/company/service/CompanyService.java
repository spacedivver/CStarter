package com.kb.company.service;

import com.kb.company.dto.company.Company;
import com.kb.company.dto.company.CompanyRequest;
import com.kb.company.dto.job.Job;
import com.kb.company.dto.job.JobResponse;
import com.kb.company.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Company createCompany(CompanyRequest companyRequest) {
        Company company = Company.builder()
                .name(companyRequest.getName())
                .title(companyRequest.getTitle())
                .isUserOption(companyRequest.getIsUserOption())
                .build();

        if (mapper.insertCompany(company) == 0) {
            return null;
        }

        return mapper.selectCompany(company.getCpno());
    }

    public List<Company> getRecommendedCompanyList(int cno) {
        List<Integer> companyIdList = mapper.selectRecommendedCompany(cno);
        List<Company> companyList = new ArrayList<>();

        for (int cpno: companyIdList) {
            companyList.add(findCompany(cpno));
        }

        return companyList;
    }

    public List<JobResponse> getCoverLetterOfJob(int cpno) {
        List<Job> jobs = mapper.selectJobByCompany(cpno);
        List<JobResponse> coverLetters = new ArrayList<>();

        for (Job job: jobs) {
            coverLetters.add(
                    JobResponse.builder()
                            .jno(job.getJno())
                            .type(job.getType())
                            .coverLetterItems(mapper.selectCoverLetterItem(job.getJno()))
                            .build());
        }

        return coverLetters;
    }
}
