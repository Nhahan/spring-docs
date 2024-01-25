package org.example.testspringfirst.service;

import lombok.RequiredArgsConstructor;
import org.example.testspringfirst.entity.Memo;
import org.example.testspringfirst.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    public List<Memo> getMemos() {
        return memoRepository.findAll();
    }

    @Transactional
    public Long saveMemo(Memo memo) {
        return memoRepository.save(memo).getId();
    }

    @Transactional
    public Long updateMemo(Long memoId, Memo input) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다."));
        memo.setTitle(input.getTitle());
        return memo.getId();
    }

    @Transactional
    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }
}
