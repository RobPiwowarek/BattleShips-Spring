package pl.piwowarek.battleships.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.piwowarek.battleships.domain.TileRepository;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private TileRepository tileRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGettingOneTileThenCorrectFieldIsReturned() throws Exception {

        String userHeader = "Basic " + new String(Base64.encodeBase64(("user:password").getBytes()));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/field?x=0&y=0")
                .header("Authorization", userHeader)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.x").value(0))
                .andExpect(jsonPath("$.y").value(0))
                .andExpect(jsonPath("$.occupied").value(false));
    }


    @Test
    @Transactional
    public void whenPostingTileAsUserThenTileIsMarkedAsBlue() throws Exception {

        String userHeader = "Basic " + new String(Base64.encodeBase64(("user:password").getBytes()));

        mockMvc.perform(
                post("/")
                        .header("Authorization", userHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"x\": 1, \"y\": 1}"));


        Assertions.assertThat(tileRepository.findByXAndY(1, 1))
                .hasFieldOrPropertyWithValue("color", "blue");
    }

    @Test
    @Transactional
    public void whenPostingTileAsUser2ThenTileIsMarkedAsGreen() throws Exception {

        String userHeader = "Basic " + new String(Base64.encodeBase64(("user2:password2").getBytes()));

        mockMvc.perform(
                post("/")
                        .header("Authorization", userHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"x\": 1, \"y\": 1}"));


        Assertions.assertThat(tileRepository.findByXAndY(1, 1))
                .hasFieldOrPropertyWithValue("color", "green");
    }

}