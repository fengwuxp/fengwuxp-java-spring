package com.oak.member.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Schema(description = "个人实名信息")
@Entity
@Table(name = "t_member_personal", indexes = {
        @Index(columnList = "id")
})
@Data
public class MemberPersonal implements java.io.Serializable {

    private static final long serialVersionUID = 5184062339308735415L;
    @Id
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "姓名")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "身份证号")
    @Column(name = "id_number", nullable = false, unique = true, length = 18)
    private String idNumber;

    @Schema(description = "证件生效日期")
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @Schema(description = "证件截止日期")
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Schema(description = "正面身份证照")
    @Column(name = "face_pic")
    private String facePic;

    @Schema(description = "反面身份证照")
    @Column(name = "back_pic")
    private String backPic;

    @Schema(description = "手持身份证照")
    @Column(name = "hand_pic")
    private String handPic;


    @Schema(description = "核验人员")
    @Column(name = "check_operator", nullable = true)
    private String checkOperator;

    @Schema(description = "核验日期")
    @Column(name = "check_time", nullable = true, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkTime;

    @Schema(description = "核验结果")//为空表示未核验
    @Column(name = "check_result", nullable = true)
    private Boolean checkResult;

    @Schema(description = "核验结果信息")
    @Column(name = "check_info", nullable = true)
    private String checkInfo;


    @Schema(description = "登记日期")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


}
