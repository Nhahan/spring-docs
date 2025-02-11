package com.example.library.service;

import com.example.library.dto.UserRequestDto;
import com.example.library.dto.UserResponseDto;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (requestDto.getEmail() == null) {
            throw new IllegalArgumentException("Email is required.");
        }
        User user = new User(requestDto.getEmail());
        User saved = userRepository.save(user);
        return new UserResponseDto(saved.getId(), saved.getEmail());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponseDto(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found with id " + id));
        return new UserResponseDto(user.getId(), user.getEmail());
    }
}
