package com.oak.api.services.infoprovide.info;

import com.oak.api.enums.ClientType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;


@Schema(description = "客户端渠道信息")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"code"})
public class ClientChannelInfo implements java.io.Serializable {


    private static final long serialVersionUID = 6244504706367201459L;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "客户端类型")
    private ClientType clientType;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "启用")
    private Boolean enabled;

    @Schema(description = "创建日期")
    private Date creatTime;

    @Schema(description = "更新日期")
    private Date updateTime;

    public ClientChannelInfo(String code) {
        this.code = code;
    }

}
