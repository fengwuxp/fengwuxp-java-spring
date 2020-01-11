package com.oak.api.services.log;

import com.oak.api.services.log.info.OperationalLogInfo;
import com.oak.api.services.log.req.CreateOperationalLogReq;
import com.oak.api.services.log.req.FindOperationalLogReq;
import com.oak.api.services.log.req.QueryOperationalLogReq;
import com.wuxp.api.log.ApiLogRecorder;
import com.wuxp.api.model.Pagination;

/**
 * 操作日志相关服务
 */
public interface OperationalLogService extends ApiLogRecorder {

    Long createOperationalLog(CreateOperationalLogReq req);

//    @Desc("编辑操作日志")
//    boolean editOperationalLog(EditOperationalLogReq req);


//    void delOperationalLog(DelOperationalLogReq req);


    OperationalLogInfo findOperationalLog(FindOperationalLogReq req);


    Pagination<OperationalLogInfo> queryOperationalLog(QueryOperationalLogReq req);
}
