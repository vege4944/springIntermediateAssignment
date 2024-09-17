package com.sparta.board.cotroller;

import com.sparta.board.dto.user.UserRequestDto;
import com.sparta.board.dto.user.UserResponseDto;
import com.sparta.board.entity.User;
import com.sparta.board.service.UserService;
import lombok.Getter;
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
    public UserResponseDto saveUser (@RequestBody UserRequestDto userSaveRequestDto){
        return userService.saveUser(userSaveRequestDto);
    }

    // 유저 단건 조회
    @GetMapping("/{userId}")
    public UserResponseDto getDetailUserByUserId (@PathVariable Long userId){
        return userService.getDetailUserByUserId (userId);
    }

    // 유저 다건 조회
    @GetMapping
    public List<UserResponseDto> getAllUser (){
        return userService.getAllUser();
    }

    // 유저 수정
    @PutMapping("/{userId}")
    public UserResponseDto updateUserByUserId (
            @PathVariable Long userId,
            @RequestBody UserRequestDto userRequestDto
    ){
        return userService.updateUserByUserId(userId, userRequestDto);
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public void deleteUserByUserId (@PathVariable Long userId) {
        userService.deleteUserByUserId(userId);
    }
}
