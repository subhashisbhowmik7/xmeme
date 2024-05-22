package com.crio.starter.exchange;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemeRequestDto {
    @NotBlank
    private String id;

    @NotBlank
    private String caption;

    @NotBlank
    private String url;
}
