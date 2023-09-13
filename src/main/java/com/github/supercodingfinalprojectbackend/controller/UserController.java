package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.PaymoneyDto;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.Oauth2Service;
import com.github.supercodingfinalprojectbackend.service.UserService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "회원 API")
public class UserController {

    private final Oauth2Service oauth2Service;
    private final UserService userService;

    @PostMapping(value = "/role/join/mentor")
    @Operation(summary = "멘토 등록")
    public ResponseEntity<ResponseUtils.ApiResponse<MentorDto.JoinResponse>> joinMentor(
            @RequestBody @Parameter(name = "멘토 등록 요청 객체", required = true) MentorDto.JoinRequest request
    ){
        ValidateUtils.requireTrue(request.validate(), ApiErrorCode.INVALID_REQUEST_BODY);

        Long userId = AuthUtils.getUserId();
        MentorDto.JoinResponse response = oauth2Service.joinMentor(userId, request);
        return ResponseUtils.created("멘토 등록에 성공했습니다.", response);
    }

    @PostMapping("/paymoney")
    @Operation(summary = "페이머니 충전")
    public ResponseEntity<ResponseUtils.ApiResponse<PaymoneyDto.ChargeResponse>> chargePaymoney(@RequestBody PaymoneyDto.ChargeRequest request) {
        ValidateUtils.requireTrue(request.validate(), ApiErrorCode.INVALID_REQUEST_BODY);

        Long userId = AuthUtils.getUserId();

        PaymoneyDto.ChargeResponse response = userService.chargePaymoney(userId, request);
        return ResponseUtils.ok("페이머니를 성공적으로 충전했습니다!", response);
    }
}
