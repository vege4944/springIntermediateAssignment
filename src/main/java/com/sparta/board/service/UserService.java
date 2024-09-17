package com.sparta.board.service;

import com.sparta.board.dto.user.*;
import com.sparta.board.entity.User;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    // 유저 생성
    @Transactional
    public UserSaveResponseDto saveUser(UserSaveRequestDto userSaveRequestDto) {
        User newUser = new User(
                userSaveRequestDto.getUsername(),
                userSaveRequestDto.getEmail()
        );
        User user = userRepository.save(newUser);
        return new UserSaveResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 유저 단건 조회
    public UserGetResponseDto getDetailUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NullPointerException("찾으시는 유저가 없습니다."));
        return new UserGetResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 유저 다건 조회 (1. 내가 처음에 배운 방법, 다른 2가지 방법은 맨 아래 주석 참조)
    public List<UserGetResponseDto> getAllUser() {
        List<User> userList = userRepository.findAll();

        List<UserGetResponseDto> dtoList = new ArrayList<>();
        for (User user : userList) {
            UserGetResponseDto dto = new UserGetResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    // 유저 수정
    @Transactional
    public UserUpdateResponseDto updateUserByUserId(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NullPointerException("찾으시는 유저가 없습니다."));
        user.updateUser(
                userUpdateRequestDto.getUsername(),
                user.getEmail()
        );
        return new UserUpdateResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 유저 삭제
    @Transactional
    public void deleteUserByUserId(Long userId) {
        if (!userRepository.existsById(userId)){ // ! = NOT
            throw new NullPointerException("찾으시는 유저가 없습니다.");
        }
        userRepository.deleteById(userId);
    }

    // 유저 다건 조회 아래처럼 쓸 수도 있음 (2. 숙련 해설강의)
    /**
     *     public List<UserDetailResponseDto> getUsers() {
     *         List<User> userList = userRepository.findAll();
     *
     *         List<UserDetailResponseDto> dtoList = new ArrayList<>();
     *         for (User user : userList) {
     *             dtoList.add(new UserDetailResponseDto(
     *                     user.getId(),
     *                     user.getUsername(),
     *                     user.getEmail(),
     *                     user.getCreatedAt(),
     *                     user.getModifiedAt()
     *             ));
     *         }
     *         return dtoList;
     *     }



     */
    // 유저 다건 조회 아래처럼 쓸 수도 있음(3. 챗 지피티)
    /**
     * public List<UserDetailResponseDto> getUsers() {
     *         return userRepository.findAll().stream()
     *                 .map(user -> new UserDetailResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt()))
     *                 .collect(Collectors.toList());
     *     }
     */

}
