package com.oak.api.helper;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.api.model.PageInfo;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.oak.api.model.ApiBaseQueryReq.TABLE_ALIAS;

@Slf4j
public final class SimpleCommonDaoHelper {


    private SimpleCommonDaoHelper() {
    }

    /**
     * 查询对象
     *
     * @param jpaDao
     * @param entityType
     * @param targetClass
     * @param req
     * @param <T>
     * @return
     */
    public static <T> Pagination<T> queryObject(JpaDao jpaDao,
                                                Class<?> entityType,
                                                Class<T> targetClass,
                                                ApiBaseQueryReq req) {

        SelectDao<?> selectDao = jpaDao.selectFrom(entityType, TABLE_ALIAS).appendByQueryObj(req);

        PageInfo<T> pageInfo = PageInfo.newInstance(req);

        /*总数查询*/
        if (req.getQueryType().isQueryNum()) {
            int count = (int) selectDao.count();
            pageInfo.setTotal(count);
        }

        /*结果集查询*/
        if (req.getQueryType().isQueryResult()) {

            if (!req.isOrderBy()) {
                //设置默认排序
                req.setDefaultOrderById();
            }
            List<T> records = selectDao.page(req.getQueryPage(), req.getQuerySize())
                    .orderBy(req.getOrderByArr(TABLE_ALIAS))
                    .find(targetClass);

            pageInfo.setRecords(records);
        }
        return pageInfo;
    }
}
