package com.crio.starter.exchange;

import com.crio.starter.data.Meme;
import lombok.Data;

@Data
public class MemeResponseDto {
    private String id;
    private String name;
    private String caption;
    private String url;

    public MemeResponseDto(Meme meme) {
        this.id = meme.getId();
        this.name = meme.getName();
        this.caption = meme.getCaption();
        this.url = meme.getUrl();
    }
}
