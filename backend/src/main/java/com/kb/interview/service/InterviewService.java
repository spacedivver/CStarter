package com.kb.interview.service;

import com.kb.interview.dto.coverletter.CoverLetterAIModelRequest;
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

    public CoverLetterQuestionResponse createSubQuestion(CoverLetterAIModelRequest request) {
        List<String> result = new ArrayList<>();
        try {
            File workingDirectory = new File(aiDirectoryPath);
            String scriptPath = "furtherQue.py";

            ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath,
                    Integer.toString(request.getClno()), Integer.toString(request.getNumber()), Integer.toString(request.getRno()));
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

        return interviewMapper.selectCoverLetterQuestion(request.getClno(), request.getNumber(), request.getQuestionType());
    }

    public List<TechQuestionResponse> getTechQuestions() {
        return interviewMapper.selectTechQuestion();
    }

    public TechQuestionResponse getTechQuestion(int bno) {
        return interviewMapper.selectTechQuestionById(bno);
    }

    public void executeCoverLetterTTS(CoverLetterAIModelRequest request) {
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

    public String executeCoverLetterSTT(CoverLetterAIModelRequest request) {
        List<String> result = new ArrayList<>();
        try {
            File workingDirectory = new File(aiDirectoryPath);
            String scriptPath = "basic_stt.py";

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

        return interviewMapper.selectCoverLetterQuestionAnswer(request.getClno(), request.getNumber(), request.getQuestionType());
    }
}