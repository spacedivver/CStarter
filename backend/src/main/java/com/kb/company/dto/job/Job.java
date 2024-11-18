package com.kb.company.dto.job;

import com.kb.company.dto.job.CoverLetterItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
    int cpno;
    int jno;
    String type;
}
