package hello.api.controller;

import static hello.api.enumforexception.AdminSentenceExceptionEnum.ADD_SENTENCE_STRING_BLANK;
import static hello.api.enumforexception.AdminSentenceExceptionEnum.NO_MATCH_GRAMMAR_ENUM;
import static hello.api.enumforexception.AdminSentenceExceptionEnum.NO_MATCH_SITUATION_ENUM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.api.dto.AdminSentenceDto;
import hello.api.dto.AdminSentenceSuccess;
import hello.api.dto.ErrorResponse;
import hello.api.entity.AdminSentenceEntity;
import hello.api.enumforentity.Grammar;
import hello.api.enumforentity.Situation;
import hello.api.exceptionadvice.AdminSentenceExceptionAdvice;
import hello.api.interceptor.ExceptionResponseInterceptor;
import hello.api.repository.AdminSentenceRepository;
import hello.api.service.AdminSentenceService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
@Slf4j
class AdminSentenceControllerTest {

    @Autowired
    AdminSentenceController adminSentenceController;
    @Autowired
    AdminSentenceRepository adminSentenceRepository;
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    String baseUrl;

    @BeforeEach
    void beforeEach() {
        objectMapper = new ObjectMapper();
        baseUrl = "/api/admin/sentence";
        mockMvc = MockMvcBuilders.standaloneSetup(adminSentenceController)
                .setControllerAdvice(new AdminSentenceExceptionAdvice())
                .addInterceptors(new ExceptionResponseInterceptor())
                .build();
    }

    @Test
    void addSentence() throws Exception {
        //given
        String url = baseUrl + "/add";
        String requestJson = "{\"korean\":\"korean\", \"english\":\"english\", "
            + "\"grammar\":\"HAVE PP\", \"situation\":\"NO\" }";
        AdminSentenceSuccess success = new AdminSentenceSuccess(201, null, null,null,null);
        String responseContent = objectMapper.writeValueAsString(success);
        //when
        //then
        mockMvc.perform(post(url)
                .contentType("application/json")
                .content(requestJson))
            .andExpect(status().isCreated())
            .andExpect(content().json(responseContent))
            .andDo(log());
    }

    @Test
    void addSentenceException1() throws Exception {
        //given
        String url = baseUrl + "/add";
        String requestJson = "{\"korean\":\"\", \"english\":\"english\", "
            + "\"grammar\":\"HAVE PP\", \"situation\":\"NO\" }";
        ErrorResponse error = new ErrorResponse(ADD_SENTENCE_STRING_BLANK.getErrorType(),
            ADD_SENTENCE_STRING_BLANK.getErrorTitle(), 400,
            ADD_SENTENCE_STRING_BLANK.getErrormessage(), "/api/admin/sentence/add");
        String responseContent = objectMapper.writeValueAsString(error);
        log.info(responseContent);
        //when
        //then
        mockMvc.perform(post(url)
                .contentType("application/json")
                .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(responseContent))
            .andDo(log());
    }

    @Test
    void addSentenceException2() throws Exception {
        //given
        String url = baseUrl + "/add";
        String requestJson = "{\"korean\":\"korean\", \"english\":\"english\", "
            + "\"grammar\":\"NO-MATCH\", \"situation\":\"NO\" }";
        ErrorResponse error = new ErrorResponse(NO_MATCH_GRAMMAR_ENUM.getErrorType(),
            NO_MATCH_GRAMMAR_ENUM.getErrorTitle(), 400,
            NO_MATCH_GRAMMAR_ENUM.getErrormessage(), "/api/admin/sentence/add");
        String responseContent = objectMapper.writeValueAsString(error);
        log.info(responseContent);
        //when
        //then
        mockMvc.perform(post(url)
                .contentType("application/json")
                .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(responseContent))
            .andDo(log());
    }

    @Test
    void addSentenceException3() throws Exception {
        //given
        String url = baseUrl + "/add";
        String requestJson = "{\"korean\":\"korean\", \"english\":\"english\", "
            + "\"grammar\":\"HAVE PP\", \"situation\":\"NO-MATCH\" }";
        ErrorResponse error = new ErrorResponse(NO_MATCH_SITUATION_ENUM.getErrorType(),
            NO_MATCH_SITUATION_ENUM.getErrorTitle(), 400,
            NO_MATCH_SITUATION_ENUM.getErrormessage(), "/api/admin/sentence/add");
        String responseContent = objectMapper.writeValueAsString(error);
        log.info(responseContent);
        //when
        //then
        mockMvc.perform(post(url)
                .contentType("application/json")
                .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(responseContent))
            .andDo(log());
    }

    @Test
    void findSelectValue() throws Exception {
        //given
        String url = baseUrl + "/selection";

        List<String> grammarList = Arrays.stream(Grammar.values()).map(Grammar::getStringGrammar)
            .collect(Collectors.toList());
        List<String> situationList = Arrays.stream(Situation.values())
            .map(Situation::getStringSituation)
            .collect(Collectors.toList());
        AdminSentenceSuccess success = new AdminSentenceSuccess(200, null, null, grammarList,
            situationList);
        String responseContent = objectMapper.writeValueAsString(success);
        //when
        //then
        mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().json(responseContent))
            .andDo(log());
    }

    @Test
    void findAll() throws Exception {
        //given
        String url = baseUrl + "/all";
        AdminSentenceEntity save1 = adminSentenceRepository.save(
            new AdminSentenceEntity("korean1", "english1", Grammar.IF, Situation.NO));
        AdminSentenceEntity save2 = adminSentenceRepository.save(
            new AdminSentenceEntity("korean2", "english2", Grammar.NO, Situation.BUSINESS));

        List<AdminSentenceDto> dtoList = Arrays.asList(
            new AdminSentenceDto(save1.getId(), save1.getKorean(), save1.getEnglish(),
                save1.getGrammar().getStringGrammar(), save1.getSituation().getStringSituation()),
            new AdminSentenceDto(save2.getId(), save2.getKorean(), save2.getEnglish(),
                save2.getGrammar().getStringGrammar(), save2.getSituation().getStringSituation()));

        AdminSentenceSuccess success = new AdminSentenceSuccess(200, null, dtoList, null,
            null);
        String responseContent = objectMapper.writeValueAsString(success);
        //when
        //then
        mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().json(responseContent))
            .andDo(log());
    }

    @Test
    void delete() throws Exception{
        //given
        AdminSentenceEntity save = adminSentenceRepository.save(
            new AdminSentenceEntity("korean1", "english1", Grammar.IF, Situation.NO));
        String url = baseUrl + "/delete/"+save.getId();
        AdminSentenceSuccess success = new AdminSentenceSuccess(200, null, null, null,
            null);
        String responseContent = objectMapper.writeValueAsString(success);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(url))
            .andExpect(status().isOk())
            .andExpect(content().json(responseContent))
            .andDo(log());
    }
}