package com.innovationserver.model;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class ValidList<E> implements List<E> {
    // json이 배열형태면 유효성검사 제대로 진행되지 않아 유효성 검사를 위한 ValidList Class
    @SuppressWarnings("unchecked")
    @Valid
    @Delegate
    private List<E> list = new ArrayList<>();
}