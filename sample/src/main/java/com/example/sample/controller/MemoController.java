package com.example.sample.controller;

import com.example.sample.entity.Memo;
import com.example.sample.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/memos")
    public List<Memo> getMemos() {
        return memoService.getMemos();
    }

    @GetMapping("/memos/{id}")
    public Memo getMemo(
            @PathVariable("id") Long id
    ) {
        return memoService.getMemo(id);
    }

    @PostMapping("/memos")
    public Long postMemo(
            @RequestBody Memo memo
    ) {
        return memoService.postMemo(memo);
    }

}
