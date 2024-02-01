package org.example.springbasicexample.dto.response;

import lombok.Getter;

@Getter
public class MemoResponse {
    private Long id;
    private String title;

    public MemoResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
