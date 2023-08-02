package com.innovationserver.service;

import com.innovationserver.dao.ListDao;
import com.innovationserver.model.ListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSvc {

    @Autowired
    ListDao listDao;

    public int doInsert(List<ListReq> listVos) {
        int i = listDao.doInsert(listVos);

        return i;
    }

    public List<ListReq> selectAll() {

        return listDao.selectAll();
    }

    public void deleteList(List<Integer> ids) {

        listDao.deleteList(ids);
    }

    public void updateList(List<ListReq> listVos) {
        listDao.updateList(listVos);
    }

    public boolean existId(int id) {
        if(listDao.existId(id) != null){
            return true;
        }
        return false;
    }
}
