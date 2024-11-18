package com.kb.interview.dto.coverletter;

import com.kb.interview.dto.coverletter.CoverLetterAnswer;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoverLetterResponse {
    private int clno;
    private int mno;
    private int cpno;
    private int jno;
    List<CoverLetterAnswer> answers;
}
