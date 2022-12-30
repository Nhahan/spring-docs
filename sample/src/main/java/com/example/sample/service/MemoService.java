package com.example.sample.service;

import com.example.sample.entity.Memo;
import com.example.sample.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    @Transactional(readOnly = true)
    public List<Memo> getMemos() {
        return memoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Memo getMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found"));
    }

    @Transactional
    public Long postMemo(Memo memo) {
        return memoRepository.save(memo).getId();
    }

}
