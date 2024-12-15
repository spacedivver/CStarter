package com.kb.interview.mapper;

import com.kb.interview.dto.report.Report;

import java.util.List;

public interface ReportMapper {
    int insertReport(Report report);
    Report selectById(int rno);
    List<Report> selectByMemberId(int mno);
}
