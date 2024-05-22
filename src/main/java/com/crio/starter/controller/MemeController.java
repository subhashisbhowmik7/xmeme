package com.crio.starter.controller;

import com.crio.starter.data.Meme;
import com.crio.starter.exchange.MemeRequestDto;
import com.crio.starter.exchange.MemeResponseDto;
import com.crio.starter.service.MemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemeController {

    @Autowired
    private final MemeService memeService;

    @PostMapping("/memes")
    public ResponseEntity<Meme> createMeme(@Valid  @RequestBody Meme meme) {
    
        return memeService.saveMeme(meme);
    }

    @GetMapping("/memes/{id}")
    public Meme getMemeById(@PathVariable String id) {
        return memeService.getMeme(id);
    }

    @GetMapping("/memes")
    public List<Meme> getAllMemes() {
        return memeService.getAllMemes();
    }

    @PutMapping("/memes")
    public MemeResponseDto updateMeme(@Valid @RequestBody MemeRequestDto memeRequestDto) {
        return memeService.updateMeme(memeRequestDto);
    }

    @DeleteMapping("/memes/{id}")
    public void deleteMeme(@PathVariable String id) {
        memeService.deleteMeme(id);
    }
}
