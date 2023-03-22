package edu.baylor.ecs.cms.service;

import edu.baylor.ecs.cms.dto.ExamDto;
import edu.baylor.ecs.cms.model.Exam;
import edu.baylor.ecs.cms.model.ExamStatus;
import edu.baylor.ecs.cms.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmsService {

    @Value("${ems.ip}")
    private String ip;

    @Value("${ems.exam}")
    private String examContext;

    @Autowired
    private UmsService umsService;

    private final RestTemplate restTemplate;
    private String TEST_URI = "https://www.newURI.org/test/";

    public EmsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Object createExam(ExamDto examDto) {
        String uri = ip + examContext;
        return this.restTemplate.postForObject(uri, examDto, ExamDto.class);
    }

    public List<Exam> getExams() {
        String uri = ip + examContext;
        Exam[] exams = this.restTemplate.getForObject(uri, Exam[].class);
        if (exams == null) {
            return null;
        }
        return Arrays.asList(exams);
    }

    public List<Question> getQuestionsForExam(Integer id) {
        String uri = ip + examContext + "/" + id + "/questions";
        Question[] questions = this.restTemplate.getForObject(uri, Question[].class);
        if (questions == null) {
            return null;
        }
        return Arrays.asList(questions);
    }

    public List<Exam> getINITExams() {
        String uri = ip + '/' + examContext;
        Exam[] exams = this.restTemplate.getForObject(uri, Exam[].class);
        if (exams == null) {
            return null;
        }
        return Arrays.asList(exams)
                .stream()
                .filter(x -> x.getStatus() == ExamStatus.INIT)
                .collect(Collectors.toList());
    }

    public String deleteINITExam(Integer id) {
        String uri = ip + examContext + "/" + id;
        this.restTemplate.delete(uri);

        return "Delete successful";
    }

    // public String testCallWithURIVar(Integer id) {
    //     String uri = "https://www.newURI.org/work";
    //     Exam[] exams = this.restTemplate.getForObject(uri, Exam[].class);
    //     return "testCallWithURIVar success!";
    // }
    // public String testCallWithOutsideVar(Integer id) {
        
    //     Exam[] exams = this.restTemplate.getForObject(this.TEST_URI + "A", Exam[].class);
    //     return "testCallWithOutsideVar success!";
    // }
}