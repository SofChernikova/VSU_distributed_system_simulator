package cs.vsu.ru.simulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs.vsu.ru.simulator.dto.RequestDto;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    private static final String URL = "/api/messages/%s";

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static Stream<Arguments> prepareData() {
        return Stream.of(
                Arguments.of(0, new RequestDto()
                        .setMessage("Hello from 0!")
                        .setSendToNeighbors(true)),
                Arguments.of(0, new RequestDto()
                        .setMessage("Hello from 0!")
                        .setSendToNeighbors(false)),
                Arguments.of(1, new RequestDto()
                        .setMessage("switch")
                        .setSendToNeighbors(true)),
                Arguments.of(1, new RequestDto()
                        .setMessage("switch")
                        .setSendToNeighbors(false)),
                Arguments.of(3, new RequestDto()
                        .setMessage("Hello from 0!")
                        .setSendToNeighbors(true))
        );
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    void test(int processId, RequestDto requestDto) throws Exception {
        mvc.perform(preparePostRequest(processId, requestDto))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    private MockHttpServletRequestBuilder preparePostRequest(
            int processId,
            RequestDto requestDto) {

        return post(URL.formatted(processId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto));
    }
}