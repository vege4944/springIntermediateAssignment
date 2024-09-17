package com.sparta.board.cotroller;

import com.sparta.board.dto.user.*;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    // 유저 생성
    @PostMapping
    public UserSaveResponseDto saveUser (@RequestBody UserSaveRequestDto userSaveRequestDto){
        return userService.saveUser(userSaveRequestDto);
    }

    // 유저 단건 조회
    @GetMapping("/{userId}")
    public UserGetResponseDto getDetailUserByUserId (@PathVariable Long userId){
        return userService.getDetailUserByUserId (userId);
    }

    // 유저 다건 조회
    @GetMapping
    public List<UserGetResponseDto> getAllUser (){
        return userService.getAllUser();
    }

    // 유저 수정
    @PutMapping("/{userId}")
    public UserUpdateResponseDto updateUserByUserId (
            @PathVariable Long userId,
            @RequestBody UserUpdateRequestDto userUpdateRequestDto
    ){
        return userService.updateUserByUserId(userId, userUpdateRequestDto);
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public void deleteUserByUserId (@PathVariable Long userId) {
        userService.deleteUserByUserId(userId);
    }
}
