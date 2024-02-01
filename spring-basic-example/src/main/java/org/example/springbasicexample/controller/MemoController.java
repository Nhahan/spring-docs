package org.example.springbasicexample.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbasicexample.dto.request.SaveMemoRequest;
import org.example.springbasicexample.dto.request.UpdateMemoRequest;
import org.example.springbasicexample.dto.response.MemoResponse;
import org.example.springbasicexample.service.MemoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/memos")
    public List<MemoResponse> getMemos() {
        return memoService.getMemos();
    }

    @GetMapping("/memos/{memoId}")
    public MemoResponse getMemo(@PathVariable Long memoId) {
        return memoService.getMemo(memoId);
    }

    @PostMapping("/memos")
    public MemoResponse saveMemo(@RequestBody SaveMemoRequest request) {
        return memoService.saveMemo(request);
    }

    @PutMapping("/memos/{memoId}")
    public MemoResponse updateMemo(@PathVariable Long memoId, @RequestBody UpdateMemoRequest request) {
        return memoService.updateMemo(memoId, request);
    }

    @DeleteMapping("/memos/{memoId}")
    public void deleteMemo(@PathVariable Long memoId) {
        memoService.deleteMemo(memoId);
    }
}
