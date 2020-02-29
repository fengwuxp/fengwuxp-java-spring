package com.wuxp.security.example.entities;

import com.levin.commons.dao.domain.support.AbstractBaseEntityObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Schema(name = "Demo例子", description = "Demo例子")
@Table(name = "t_demo")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Tag(name = "simple")
public class DemoEntity extends AbstractBaseEntityObject<Long> {

    private static final long serialVersionUID = 5273901349043495181L;
    @Id
    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    @NotNull
    @Size(max = 16)
    @Column(name = "name")
    private String name;

    @Schema(description = "年龄")
    @NotNull
    @Max(value = 200)
    @Column(name = "aeg")
    private Integer age;

    @Schema(description = "是否删除")
    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Schema(description = "创建日期")
    @Column(name = "add_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;


}
