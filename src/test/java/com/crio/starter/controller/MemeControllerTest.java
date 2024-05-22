package com.crio.starter.controller;

import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import com.crio.starter.data.Meme;
import com.crio.starter.repository.MemeRepository;
import com.crio.starter.service.MemeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers=MemeController.class)
public class MemeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemeService memeService;
    @MockBean
    private MemeRepository memeRepository;
    
    @Test
    void testAllMemesReturns200() throws Exception
    {
        Meme m1 = new Meme("Meme1", "This is meme 1", "https://www.pexels.com/photo/photo-of-paint-splatter-artwork-1000366/");
        Meme m2 = new Meme("Meme2", "This is meme 2", "https://www.pexels.com/photo/composition-of-different-conchs-on-beige-table-4226881/");
        Meme m3 = new Meme("Meme3", "This is meme 3", "https://www.pexels.com/photo/blue-abstract-background-with-wavy-lines-on-white-surface-3737018/");
    

        List<Meme> memeList=  new ArrayList<Meme>();
        memeList.add(m1);
        memeList.add(m2);
        memeList.add(m3);

        when(memeService.getAllMemes()).thenReturn(memeList);           //Mocking

        String uriPath="/memes";
        // ResultMatcher status200= MockMvcResultMatchers.status().isOk();
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get(uriPath).contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions= mockMvc.perform(requestBuilder);

        resultActions.andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());     //Status 200

    }

    

    void invalidRequestBodyReturnsBadRequest() throws Exception
    {
        Meme m1 = new Meme("Meme1", "This is meme 1", "https://www.pexels.com/photo/photo-of-paint-splatter-artwork-1000366/");
        Meme m2 = new Meme("Meme2", "This is meme 2", "https://www.pexels.com/photo/composition-of-different-conchs-on-beige-table-4226881/");
        Meme m3 = new Meme("Meme3", "This is meme 3", "https://www.pexels.com/photo/blue-abstract-background-with-wavy-lines-on-white-surface-3737018/");
      


        List<Meme> memeList=  new ArrayList<Meme>();
        memeList.add(m1);
        memeList.add(m2);
        memeList.add(m3);

        when(memeService.getAllMemes()).thenReturn(memeList);           //Mocking

        String uriPath="/memes/";
        //ResultMatcher statusisBadRequest= MockMvcResultMatchers.status().isBadRequest();
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get(uriPath).contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions= mockMvc.perform(requestBuilder);

        resultActions.andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());     

    }
    
}
