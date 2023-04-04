package hello.api.controller;

import static hello.api.enumforexception.UserManageExceptionEnum.DUPLICATED_SIGNUP_EMAIL;
import static hello.api.enumforexception.UserManageExceptionEnum.SIGNUP_REQUEST_EMAIL;
import static hello.api.enumforexception.UserManageExceptionEnum.SIGNUP_REQUEST_NAME;
import static hello.api.enumforexception.UserManageExceptionEnum.SIGNUP_REQUEST_PASSWORD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.api.dto.ErrorResponse;
import hello.api.entity.Users;
import hello.api.exceptionadvice.UserManageExceptionAdvice;
import hello.api.interceptor.ExceptionResponseInterceptor;
import hello.api.repository.UserRepository;
import hello.api.threadlocalstorage.ErrorInformationTlsContainer;
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
class UserManageControllerTest {

//    @Autowired
//    UserManageController userManageController;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ErrorInformationTlsContainer errorInformationTlsContainer;
//    MockMvc mockMvc;
//    ObjectMapper objectMapper;
//    String baseUrl;
//
//    @BeforeEach
//    void beforeEach() {
//        objectMapper = new ObjectMapper();
//        baseUrl = "/api/user/manage";
//        mockMvc = MockMvcBuilders.standaloneSetup(userManageController)
//            .setControllerAdvice(new UserManageExceptionAdvice(errorInformationTlsContainer))
//            .addInterceptors(new ExceptionResponseInterceptor(errorInformationTlsContainer))
//            .build();
//    }
//    @Test
//    void signup() throws Exception {
//        //given
//        String url = baseUrl + "/signup";
//        String requestJson = "{\"username\":\"email@naver.com\", \"password\":\"AAss!!123456\", "
//            + "\"name\":\"name\"}";
//        //when
//        //then
//        mockMvc.perform(post(url)
//                .contentType("application/json")
//                .content(requestJson)
//            )
//            .andExpect(status().isCreated())
//            .andDo(log());
//    }
//
//    @Test
//    void signupException1() throws Exception {
//        //given
//        String url = baseUrl + "/signup";
//        String requestJson = "{\"username\":\"email@naver.com\", \"password\":\"AAss!!123456\", "
//            + "\"name\":\"name\"}";
//
//        userRepository.save(
//            Users.builder().username("email@naver.com").password("BBdd!!123456").name("saveName")
//                .build());
//        ErrorResponse error = new ErrorResponse(DUPLICATED_SIGNUP_EMAIL.getErrorType(),
//            DUPLICATED_SIGNUP_EMAIL.getErrorTitle(), 409,
//            DUPLICATED_SIGNUP_EMAIL.getErrormessage(), "/api/user/manage/signup");
//        String responseContent = objectMapper.writeValueAsString(error);
//        //when
//        //then
//        mockMvc.perform(post(url)
//                .contentType("application/json")
//                .content(requestJson)
//            )
//            .andExpect(status().isConflict())
//            .andExpect(content().json(responseContent))
//            .andDo(log());
//    }
//
//    @Test
//    void signupException2() throws Exception {
//        //given
//        String url = baseUrl + "/signup";
//        String requestJson = "{\"username\":\"noEmailPattern\", \"password\":\"AAss!!123456\", "
//            + "\"name\":\"name\"}";
//        ErrorResponse error = new ErrorResponse(SIGNUP_REQUEST_EMAIL.getErrorType(),
//            SIGNUP_REQUEST_EMAIL.getErrorTitle(), 400,
//            SIGNUP_REQUEST_EMAIL.getErrormessage(), "/api/user/manage/signup");
//        String responseContent = objectMapper.writeValueAsString(error);
//        //when
//        //then
//        mockMvc.perform(post(url)
//                .contentType("application/json")
//                .content(requestJson)
//            )
//            .andExpect(status().isBadRequest())
//            .andExpect(content().json(responseContent))
//            .andDo(log());
//    }
//
//    @Test
//    void signupException3() throws Exception {
//        //given
//        String url = baseUrl + "/signup";
//        String requestJson = "{\"username\":\"email@naver.com\", \"password\":\"wrongPasswordPattern\", "
//            + "\"name\":\"name\"}";
//        ErrorResponse error = new ErrorResponse(SIGNUP_REQUEST_PASSWORD.getErrorType(),
//            SIGNUP_REQUEST_PASSWORD.getErrorTitle(), 400,
//            SIGNUP_REQUEST_PASSWORD.getErrormessage(), "/api/user/manage/signup");
//        String responseContent = objectMapper.writeValueAsString(error);
//        //when
//        //then
//        mockMvc.perform(post(url)
//                .contentType("application/json")
//                .content(requestJson)
//            )
//            .andExpect(status().isBadRequest())
//            .andExpect(content().json(responseContent))
//            .andDo(log());
//    }
//
//    @Test
//    void signupException4() throws Exception {
//        //given
//        String url = baseUrl + "/signup";
//        String requestJson = "{\"username\":\"email@naver.com\", \"password\":\"AAss!!123456\", "
//            + "\"name\":\"\"}";
//        ErrorResponse error = new ErrorResponse(SIGNUP_REQUEST_NAME.getErrorType(),
//            SIGNUP_REQUEST_NAME.getErrorTitle(), 400,
//            SIGNUP_REQUEST_NAME.getErrormessage(), "/api/user/manage/signup");
//        String responseContent = objectMapper.writeValueAsString(error);
//        //when
//        //then
//        mockMvc.perform(post(url)
//                .contentType("application/json")
//                .content(requestJson)
//            )
//            .andExpect(status().isBadRequest())
//            .andExpect(content().json(responseContent))
//            .andDo(log());
//    }
}