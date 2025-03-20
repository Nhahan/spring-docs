package org.example.expert.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoSearchDto {
    private final Long todoId;
    private final String todoTitle;
    private final Long managerCount;
    private final Long commentCount;
}
