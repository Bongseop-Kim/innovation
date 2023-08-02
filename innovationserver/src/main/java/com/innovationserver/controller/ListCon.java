package com.innovationserver.controller;

import com.innovationserver.model.DefaultRes;
import com.innovationserver.model.ListReq;
import com.innovationserver.service.ListSvc;
import com.innovationserver.model.ValidList;
import com.innovationserver.utils.ResponseMessage;
import com.innovationserver.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class ListCon {

    @Autowired
    ListSvc listSvc;

    private DefaultRes<List<String>> processErrors(BindingResult bindingResult) {
        //에러 메시지를 배열에 담는 로직
        Set<String> uniqueErrorMessages = new HashSet<>();
        //중복된 메시지는 Set를 이용해 중복을 제거

        for (FieldError error : bindingResult.getFieldErrors()) {
            uniqueErrorMessages.add(error.getDefaultMessage());
        }

        return DefaultRes.res(false, StatusCode.BAD_REQUEST, String.join(", ", uniqueErrorMessages));
    }

    @PostMapping("")
    public ResponseEntity insertList(@RequestBody @Valid ValidList<ListReq> listReqs, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //유효성 검사
            DefaultRes<List<String>> errorResponse = processErrors(bindingResult);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        if (listReqs.size() > 10) {
            // 선택 사항이 10개 이상이면 에러를 반환하는 로직
            return ResponseEntity.badRequest().body(DefaultRes.res(false, StatusCode.BAD_REQUEST, "항목은 최대 10개 까지 선택 가능합니다."));
        }

        listSvc.doInsert(listReqs);

        return new ResponseEntity(DefaultRes.res(true,StatusCode.CREATED, ResponseMessage.CREATE_LIST), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity searchList(){
        return new ResponseEntity(DefaultRes.res(true,StatusCode.OK, ResponseMessage.READ_LIST,listSvc.selectAll()), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity deleteList(@RequestBody List<Integer> ids){
        List<Integer> nonExistentIds = new ArrayList<>();

        for (Integer id : ids) {
            // id에 해당하는 데이터가 존재하는지 확인하는 로직
            if (!listSvc.existId(id)) {
                nonExistentIds.add(id);
            }
        }
        // 존재 하지 않는 id를 배열에 넣는 로직
        String nonExistentIdsAsString = nonExistentIds.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        if (!nonExistentIds.isEmpty()) {
            return new ResponseEntity(DefaultRes.res(false,StatusCode.NOT_FOUND,"일치하지 않는 아이디: " + nonExistentIdsAsString),HttpStatus.NOT_FOUND);
        }
        listSvc.deleteList(ids);

        return new ResponseEntity(DefaultRes.res(true,StatusCode.OK, ResponseMessage.DELETE_LIST), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateList(@RequestBody @Valid ValidList<ListReq> listReqs,BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            DefaultRes<List<String>> errorResponse = processErrors(bindingResult);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        List<Integer> nonExistentIds = new ArrayList<>();

        for (ListReq listVo : listReqs) {
            // id에 해당하는 데이터가 존재하는지 확인하는 로직
            if (!listSvc.existId(listVo.getId())) {
                nonExistentIds.add(listVo.getId());
            }
        }
        // 존재 하지 않는 id를 배열에 넣는 로직
        String nonExistentIdsAsString = nonExistentIds.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        if (!nonExistentIds.isEmpty()) {
            return new ResponseEntity(DefaultRes.res(false,StatusCode.NOT_FOUND,"일치하지 않는 아이디: " + nonExistentIdsAsString),HttpStatus.NOT_FOUND);
        }
        if (listReqs.size() > 10) {
            return ResponseEntity.badRequest().body(DefaultRes.res(false, StatusCode.BAD_REQUEST, "항목은 최대 10개 까지 선택 가능합니다."));
        }
        
        
        listSvc.updateList(listReqs);

        return new ResponseEntity(DefaultRes.res(true,StatusCode.OK, ResponseMessage.UPDATE_LIST), HttpStatus.OK);
    }

}
