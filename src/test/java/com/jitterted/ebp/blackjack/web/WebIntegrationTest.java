package com.jitterted.ebp.blackjack.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest
public class WebIntegrationTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    void getHomePageIsStatus200Ok() throws Exception {
        mockMvc.perform(get("/index.html"))
                .andExpect(status().isOk());
    }

    @Test
    void postStartGameEndpointIsStatus300() throws Exception {
        mockMvc.perform(post("/start-game"))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    void passToHitEndpointIsRedirect() throws Exception {
        mockMvc.perform(post("/hit"))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    void getGameEndpointIsStatus200Ok() throws Exception {
        mockMvc.perform(get("/done"))
               .andExpect(status().isOk());
    }

    @Test
    void getDoneEndpointIs200Ok() {

    }
}
