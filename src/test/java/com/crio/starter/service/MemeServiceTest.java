package com.crio.starter.service;

import com.crio.starter.data.Meme;
import com.crio.starter.exceptions.InvalidMemeException;
import com.crio.starter.exceptions.MemeNotFoundException;
import com.crio.starter.repository.MemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemeServiceTest {

    @Mock
    private MemeRepository memeRepository;

    @InjectMocks
    private MemeServiceImpl memeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // public void testSaveMemeSuccess() {
    //     Meme meme = new Meme("1", "crio-meme", "caption", "url");
    //     when(memeRepository.findById("1")).thenReturn(Optional.empty());
    //     when(memeRepository.save(any(Meme.class))).thenReturn(meme);

    //     String savedMemeId =  memeService.saveMeme(meme);

    //     assertEquals("1", savedMemeId);
    //     verify(memeRepository, times(1)).save(meme);
    // }
            //iraada ha uncomment krne ka

@Test
void testGetAllMemesWhenEmpty() {
    // Arrange
    when(memeRepository.findAll()).thenReturn(Collections.emptyList());

    // Act
    List<Meme> memes = memeService.getAllMemes();

    // Assert
    assertTrue(memes.isEmpty());
    verify(memeRepository, times(1)).findAll();
}

@Test
void testPostFirstMeme() {
    // Arrange
    Meme meme = new Meme();
    meme.setName("Test Name");
    meme.setUrl("http://test.url");
    meme.setCaption("Test Caption");

    when(memeRepository.findByNameAndUrlAndCaption(anyString(), anyString(), anyString()))
        .thenReturn(Optional.empty());
    when(memeRepository.save(any(Meme.class))).thenAnswer(invocation -> {
        Meme savedMeme = invocation.getArgument(0);
        savedMeme.setId("1");     // Simulate MongoDB setting the ID since in mockito it wont be setting the id just like mongodb
        return savedMeme;
    });

    // Act
    ResponseEntity<Meme> response = memeService.saveMeme(meme);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody().getId());
    verify(memeRepository, times(1)).save(any(Meme.class));
}

@Test
void testGetMemeById() {
    // Arrange
    Meme meme = new Meme();
    meme.setId("1");
    meme.setName("Test Name");
    meme.setUrl("http://test.url");
    meme.setCaption("Test Caption");

    when(memeRepository.findById("1")).thenReturn(Optional.of(meme));

    // Act
    Meme foundMeme = memeService.getMeme("1");

    // Assert
    assertEquals("1", foundMeme.getId());
    assertEquals("Test Name", foundMeme.getName());
    verify(memeRepository, times(1)).findById("1");
}

@Test
void testGetMemeByInvalidId() {
    // Arrange
    when(memeRepository.findById("invalidId")).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(MemeNotFoundException.class, () -> memeService.getMeme("invalidId"));
    verify(memeRepository, times(1)).findById("invalidId");
}


@Test
void testPostDuplicateMeme() {
    // Arrange
    Meme meme = new Meme();
    meme.setName("Test Name");
    meme.setUrl("http://test.url");
    meme.setCaption("Test Caption");

    when(memeRepository.findByNameAndUrlAndCaption(anyString(), anyString(), anyString()))
        .thenReturn(Optional.of(meme));

    // Act
    ResponseEntity<Meme> response = memeService.saveMeme(meme);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    verify(memeRepository, times(1)).findByNameAndUrlAndCaption(anyString(), anyString(), anyString());
}


@Test
void testPostEmptyMeme() {
    // Arrange
    Meme meme = new Meme();

    // Act & Assert
    ResponseEntity<Meme> response = memeService.saveMeme(meme);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(memeRepository, never()).save(any(Meme.class));
}

@Test
void testGetAllMemesWhenNotEmpty() {
    // Arrange
    List<Meme> memes = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
        Meme meme = new Meme();
        meme.setId(String.valueOf(i));
        meme.setName("Test Name " + i);
        meme.setUrl("http://test.url/" + i);
        meme.setCaption("Test Caption " + i);
        memes.add(meme);
    }

    when(memeRepository.findAll()).thenReturn(memes);

    // Act
    List<Meme> returnedMemes = memeService.getAllMemes();

    // Assert
    assertEquals(50, returnedMemes.size());
    verify(memeRepository, times(1)).findAll();
}

@Test
void testGetLatest100Memes() {
    // Arrange
    List<Meme> memes = new ArrayList<>();
    for (int i = 0; i < 150; i++) {
        Meme meme = new Meme();
        meme.setId(String.valueOf(i));
        meme.setName("Test Name " + i);
        meme.setUrl("http://test.url/" + i);
        meme.setCaption("Test Caption " + i);
        meme.setCreatedAt(LocalDateTime.now().minusDays(150 - i));
        memes.add(meme);
    }

    when(memeRepository.findAll()).thenReturn(memes);       //mocking

    // Act
    List<Meme> returnedMemes = memeService.getAllMemes();

    // Assert
    assertEquals(100, returnedMemes.size());
    verify(memeRepository, times(1)).findAll();
    assertTrue(returnedMemes.get(0).getCreatedAt().isAfter(returnedMemes.get(99).getCreatedAt()));
}



}