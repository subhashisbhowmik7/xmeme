package com.crio.starter.service;

import com.crio.starter.data.Meme;
import com.crio.starter.data.MemeDateComparator;
import com.crio.starter.exceptions.DuplicateMemeException;
import com.crio.starter.exceptions.InvalidMemeException;
import com.crio.starter.exceptions.MemeNotFoundException;
import com.crio.starter.exchange.MemeRequestDto;
import com.crio.starter.exchange.MemeResponseDto;
import com.crio.starter.repository.MemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemeServiceImpl implements MemeService {
   
    @Autowired
    private final MemeRepository memeRepository;


    @Override
    public ResponseEntity<Meme> saveMeme(Meme meme) {

        if(!isValidMeme(meme))  { 
        System.out.println("Inside isValid");
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(meme);       
    }
        
            Optional<Meme> existingMeme = memeRepository.findByNameAndUrlAndCaption(meme.getName(), meme.getUrl(), meme.getCaption());
        if (existingMeme.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(meme);
        }
        meme.setCreatedAt(LocalDateTime.now());
        Meme savedMeme= memeRepository.save(meme);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeme);

        
    } 
//     @Override
// public Meme saveMeme(Meme meme) {
//     Optional<Meme> existingMeme = memeRepository.findById(meme.getId());
//     if (existingMeme.isPresent()) {
//         throw new DuplicateMemeException("Meme already exists with id: " + meme.getId());
//     } 
//     return memeRepository.save(meme);
// }


    @Override
    public Meme getMeme(String id) {
         
        return memeRepository.findById(id)
            .orElseThrow(() -> new MemeNotFoundException("Meme not found"));
    }

    @Override
    public List<Meme> getAllMemes() {
        // List<Meme> listOfAllMemes=Collections.emptyList();
        // listOfAllMemes = memeRepository.findAll()
        //         .stream()
        //         .collect(Collectors.toList());
        // Collections.sort(listOfAllMemes, new MemeDateComparator());
        //return listOfAllMemes.subList(0, 100);
        //IdxOutOfBound

        List<Meme> listOfAllMemes = memeRepository.findAll();
        Collections.sort(listOfAllMemes, new MemeDateComparator());
    
        return listOfAllMemes.stream()
                             .limit(100)
                             .collect(Collectors.toList());

        
    }

    @Override
    public MemeResponseDto updateMeme(MemeRequestDto memeRequestDto) {
        Meme meme = memeRepository.findById(memeRequestDto.getId())
                .orElseThrow(() -> new MemeNotFoundException("Meme not found"));

        meme.setCaption(memeRequestDto.getCaption());
        meme.setUrl(memeRequestDto.getUrl());
        Meme updatedMeme = memeRepository.save(meme);
        return new MemeResponseDto(updatedMeme);
    }

    @Override
    public void deleteMeme(String id) {
        memeRepository.deleteById(id);
    }


    public boolean isValidMeme(Meme meme)
    {
        if (meme.getName() == null || meme.getName().trim().equals("") || meme.getCaption() == null ||meme.getCaption().trim().equals("") ||
            meme.getUrl() == null || meme.getUrl().trim().equals("") ) {
            return false;
        }
        return true;
    }

}
