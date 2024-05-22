package com.crio.starter.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;


@Document(collection = "memes")
@Getter@Setter
public class Meme {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String caption;

    @NotBlank
    private String url;

    private LocalDateTime createdAt;

    public Meme() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Meme(String name, String caption, String url) {
        this.name = name;
        this.caption = caption;
        this.url = url;
        this.createdAt = LocalDateTime.now();
    }


}
