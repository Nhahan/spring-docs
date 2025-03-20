package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.ServerException;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.service.UserAdminService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;

    @PatchMapping("/admin/users/{userId}")
    public void changeUserRole(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable long userId,
            @RequestBody UserRoleChangeRequest userRoleChangeRequest
    ) {
        if (!userAdminService.isAdmin(authUser)) {
            throw new ServerException("권한이 없습니다.");
        }

        userAdminService.changeUserRole(userId, userRoleChangeRequest);
    }
}
