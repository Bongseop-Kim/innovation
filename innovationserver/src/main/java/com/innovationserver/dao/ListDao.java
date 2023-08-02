package com.innovationserver.dao;


import com.innovationserver.model.ListReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ListDao {

    int doInsert(List<ListReq> listVolist);

    List<ListReq> selectAll();

    void deleteList(List<Integer> ids);

    void updateList(List<ListReq> listVos);

    ListReq existId(int id);
}
