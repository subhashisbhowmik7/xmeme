package com.crio.starter.service;

import java.util.List;
import com.crio.starter.data.Meme;
import com.crio.starter.exchange.MemeRequestDto;
import com.crio.starter.exchange.MemeResponseDto;
import org.springframework.http.ResponseEntity;

public interface MemeService {

    ResponseEntity<Meme> saveMeme(Meme meme);
    Meme getMeme(String id);
    List<Meme> getAllMemes();
    MemeResponseDto updateMeme(MemeRequestDto memeRequestDto);
    void deleteMeme(String id);
}
