package com.kb.interview.service;

import com.kb.interview.dto.CoverLetter;
import com.kb.interview.mapper.CoverLetterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@Log4j
@RequiredArgsConstructor
@Service
public class CoverLetterService {
    private final CoverLetterMapper mapper;

    public CoverLetter create(int uno) {
        if (mapper.insert(uno) == 0) {
            return null;
        }

        return CoverLetter.builder().build();
    }
}
