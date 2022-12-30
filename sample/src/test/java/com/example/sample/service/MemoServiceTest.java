package com.example.sample.service;

import com.example.sample.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoServiceTest {

    @Autowired
    private MemoService memoService;

    @Test
    void crudTest() {
        // GIVEN
        Memo memo = new Memo("좋은 제목", "좋은 내용");
        Memo memo2 = new Memo("나쁜 제목", "나쁜 내용");

        // WHEN
        Long postedMemoId = memoService.postMemo(memo); // Memo 등록
        Memo getMemoResult = memoService.getMemo(postedMemoId); // Memo 가져오기
        memoService.postMemo(memo2); // Memo2 등록
        List<Memo> getMemosResult = memoService.getMemos(); // Memo List 가져오기

        // THEN
        assertThat(getMemoResult.getId()).isEqualTo(memo.getId()); // postMemo, getMemo 테스트
        assertThat(getMemosResult.size()).isEqualTo(2); // getMemos 테스트
    }

}