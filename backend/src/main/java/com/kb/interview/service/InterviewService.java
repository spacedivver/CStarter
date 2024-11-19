package com.kb.interview.service;

import com.kb.interview.dto.coverletter.CoverLetterTTSRequest;
import com.kb.interview.dto.question.CoverLetterQuestion;
import com.kb.interview.dto.question.CoverLetterQuestionRequest;
import com.kb.interview.dto.question.CoverLetterQuestionResponse;
import com.kb.interview.dto.question.CoverLetterSubQuestionRequest;
import com.kb.interview.dto.tech.TechQuestionResponse;
import com.kb.interview.mapper.CoverLetterMapper;
import com.kb.interview.mapper.InterviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

@Log4j
@RequiredArgsConstructor
@Service
@PropertySource({"classpath:/application.properties"})
public class InterviewService {
    @Value("${ai.execute.path}")
    private String aiDirectoryPath;
    private final InterviewMapper interviewMapper;
    private final CoverLetterMapper coverLetterMapper;

    public List<CoverLetterQuestionResponse> createQuestions(int rno, CoverLetterQuestionRequest request) {
        List<String> result = new ArrayList<>();
        try {
            File workingDirectory = new File(aiDirectoryPath);
            String scriptPath = "basic_createQue.py";

            ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath,
                    request.getCompanyName(), request.getJob(),
                    Integer.toString(request.getQuestionCount()), Integer.toString(request.getClno()),
                    Integer.toString(rno));
            processBuilder.directory(workingDirectory);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                result.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CoverLetterQuestionResponse> responses = interviewMapper.selectCoverLetterQuestions(request.getClno());

        for (CoverLetterQuestionResponse response: responses) {
            response.setRno(rno);
        }

        return responses;
    }

    public CoverLetterQuestionResponse createSubQuestion(CoverLetterSubQuestionRequest request) {
        // python 실행해서 꼬리질문 생성하기
       CoverLetterQuestion subQuestion = CoverLetterQuestion.builder()
                .clno(request.getClno())
                .rno(request.getRno())
                .number(request.getNumber())
                .questionType(1)
                .question(request.getNumber() + "번 질문에 대한 꼬리 질문")
                .build();
        coverLetterMapper.insertQuestion(subQuestion);
        // python 실행해서 꼬리질문 생성하기

        return interviewMapper.selectCoverLetterQuestion(request.getRno(), request.getNumber(), 1);
    }

    public List<TechQuestionResponse> getTechQuestions() {
        return interviewMapper.selectTechQuestion();
    }

    public TechQuestionResponse getTechQuestion(int bno) {
        return interviewMapper.selectTechQuestionById(bno);
    }

    public void executeCoverLetterTTS(CoverLetterTTSRequest request) {
        List<String> result = new ArrayList<>();
        try {
            File workingDirectory = new File(aiDirectoryPath);
            String scriptPath = "basic_tts.py";

            ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath,
                    Integer.toString(request.getClno()), Integer.toString(request.getNumber()));
            processBuilder.directory(workingDirectory);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                result.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}