package org.example.testspringfirst.controller;

import lombok.RequiredArgsConstructor;
import org.example.testspringfirst.entity.Memo;
import org.example.testspringfirst.service.MemoService;
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

    @PostMapping("/memos")
    public Long saveMemo(@RequestBody Memo memo) {
        return memoService.saveMemo(memo);
    }

    @PutMapping("/memos/{memoId}")
    public Long updateMemo(@PathVariable Long memoId, @RequestBody Memo input) {
        return memoService.updateMemo(memoId, input);
    }

    @DeleteMapping("/memos/{memoId}")
    public void deleteMemo(@PathVariable Long memoId) {
        memoService.deleteMemo(memoId);
    }
}
