package org.example.springbasicexample.service;

import lombok.RequiredArgsConstructor;
import org.example.springbasicexample.dto.request.SaveMemoRequest;
import org.example.springbasicexample.dto.request.UpdateMemoRequest;
import org.example.springbasicexample.dto.response.MemoResponse;
import org.example.springbasicexample.entity.Memo;
import org.example.springbasicexample.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    public List<MemoResponse> getMemos() {
        return memoRepository.findAll().stream()
                .map(memo -> new MemoResponse(memo.getId(), memo.getTitle())).collect(Collectors.toList());
    }

    public MemoResponse getMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new IllegalArgumentException("Invalid memoId"));
        return new MemoResponse(memo.getId(), memo.getTitle());
    }

    @Transactional
    public MemoResponse saveMemo(SaveMemoRequest request) {
        Memo memo = new Memo(request.getTitle());
        Memo savedMemo = memoRepository.save(memo);
        return new MemoResponse(savedMemo.getId(), savedMemo.getTitle());
    }

    @Transactional
    public MemoResponse updateMemo(Long memoId, UpdateMemoRequest request) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new IllegalArgumentException("Invalid memoId"));
        memo.update(request.getTitle());
        return new MemoResponse(memo.getId(), memo.getTitle());
    }

    @Transactional
    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }
}
