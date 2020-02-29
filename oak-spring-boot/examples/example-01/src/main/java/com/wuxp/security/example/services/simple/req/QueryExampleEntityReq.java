package com.wuxp.security.example.services.simple.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询example例子
 *  2020-2-16 10:20:18
 */
@Schema(description = "查询example例子")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryExampleEntityReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    private String name;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "头像")
    private String avatarUrl;

    @Schema(description = "账户余额")
    private Integer money;

    @Schema(description = "最小生日")
    @Gte("birthday")
    private Date minBirthday;

    @Schema(description = "最大生日")
    @Lte("birthday")
    private Date maxBirthday;

    @Schema(description = "星期")
    private Week week;

    @Schema(description = "例子id")
    private Long exampleId;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "是否允许")
    private Boolean enable;

    @Schema(description = "是否可编辑")
    private Boolean editable;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "最小更新时间")
    @Gte("lastUpdateTime")
    private Date minLastUpdateTime;

    @Schema(description = "最大更新时间")
    @Lte("lastUpdateTime")
    private Date maxLastUpdateTime;

    @Schema(description = "备注")
    private String remark;

    public QueryExampleEntityReq() {
    }

    public QueryExampleEntityReq(Long id) {
        this.id = id;
    }
}
