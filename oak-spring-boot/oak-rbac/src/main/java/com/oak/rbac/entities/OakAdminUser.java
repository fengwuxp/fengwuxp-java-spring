//package com.oak.rbac.entities;
//
//import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
//import com.levin.commons.service.domain.Desc;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.ToString;
//import lombok.experimental.Accessors;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Table(name = "t_rbac_admin", indexes = {
//
//})
//@Entity
//@Schema(description = "管理员用户")
//@Data
//@Accessors(chain = true)
//@EqualsAndHashCode(callSuper = true, of = {"id"})
//public class OakAdminUser extends AbstractNamedEntityObject<Long> {
//
//
//    @Desc("管理员ID")
//    @Id
//    private Long id;
//
//    @Desc("管理员名字")
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Desc("是否超管理")
//    @Column(name = "root", nullable = false)
//    private Boolean root = false;
//
//    @Desc("是否控制台管理员")
//    @Column(name = "mc")
//    private Boolean mc = false;
//
//    @Desc("创建人员")
//    @Column(name = "create_by", nullable = false, length = 64)
//    private String createBy;
//
//    @Desc("修改人员")
//    @Column(name = "update_by", length = 64)
//    private String updateBy;
//
//
//}
