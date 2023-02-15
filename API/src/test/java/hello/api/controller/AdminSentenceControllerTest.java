package hello.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.api.dto.AdminSentenceSuccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class AdminSentenceControllerTest {

    @Autowired
    AdminSentenceController adminSentenceController;
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    String baseUrl;

    @BeforeEach
    void beforeEach() {
        objectMapper = new ObjectMapper();
        baseUrl = "/api/admin/sentence";
        mockMvc = MockMvcBuilders.standaloneSetup(adminSentenceController).build();
    }

    @Test
    void addSentence() throws Exception {
        //given
        String url = baseUrl + "/add";
        String requestJson = "{\"korean\":\"korean\", \"english\":\"english\", "
            + "\"grammar\":\"HAVE PP\", \"situation\":\"NO\" }";
        AdminSentenceSuccess success = new AdminSentenceSuccess(201, null, null);
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
}