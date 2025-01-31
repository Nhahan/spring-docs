package com.example.jdbc.controller;

import com.example.jdbc.dto.MemoRequestDto;
import com.example.jdbc.dto.MemoResponseDto;
import com.example.jdbc.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/memos")
    public ResponseEntity<MemoResponseDto> saveMemo(@RequestBody MemoRequestDto dto) {
        return ResponseEntity.ok(memoService.saveMemo(dto));
    }

    @GetMapping("/memos")
    public ResponseEntity<List<MemoResponseDto>> findById() {
        return ResponseEntity.ok(memoService.findAll());
    }

    @GetMapping("/memos/{id}")
    public ResponseEntity<MemoResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memoService.findById(id));
    }

    @PutMapping("/memos/{id}")
    public ResponseEntity<MemoResponseDto> updateContent(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        return ResponseEntity.ok(memoService.updateContent(id, dto));
    }

    @DeleteMapping("/memos/{id}")
    public void delete(@PathVariable Long id) {
        memoService.deleteById(id);
    }
}
