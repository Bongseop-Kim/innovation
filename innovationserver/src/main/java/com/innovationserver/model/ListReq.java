package com.innovationserver.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
public class ListReq {
    private Integer id;

    @NotBlank(message = "이름은 필수(공백 불가) 항목입니다.")
    private String name;

    @NotNull(message = "번호는 필수 항목입니다.")
    private Integer number;

    @NotBlank(message = "유저아이디는 필수(공백 불가) 항목입니다.")
    private String user_id;

    @Positive(message = "수입은 양수이어야 합니다.")
    private Integer income;

    @NotNull(message = "점수는 필수 항목입니다.")
    private Integer score;
}