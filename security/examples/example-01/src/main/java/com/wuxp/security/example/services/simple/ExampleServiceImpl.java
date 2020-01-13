package com.wuxp.security.example.services.simple;

import com.levin.commons.dao.JpaDao;
import com.oak.springboot.helper.SimpleCommonDaoHelper;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.security.example.entities.ExampleEntity;
import com.wuxp.security.example.services.simple.info.ExampleInfo;
import com.wuxp.security.example.services.simple.req.CreateExampleReq;
import com.wuxp.security.example.services.simple.req.DeleteExampleReq;
import com.wuxp.security.example.services.simple.req.EditExampleReq;
import com.wuxp.security.example.services.simple.req.QueryExampleReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExampleServiceImpl implements ExampleService {

    @Autowired
    private JpaDao jpaDao;


    @Override
    public ApiResp<Long> create(CreateExampleReq req) {
        return null;
    }

    @Override
    public ApiResp<Void> edit(EditExampleReq req) {
        return null;
    }

    @Override
    public ApiResp<Void> delete(DeleteExampleReq req) {
        return null;
    }

    @Override
    public Pagination<ExampleInfo> query(QueryExampleReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao, ExampleEntity.class, ExampleInfo.class, req);
    }

    @Override
    public ExampleInfo findById(Long id) {
        return this.query(new QueryExampleReq(id)).getFirst();
    }
}
