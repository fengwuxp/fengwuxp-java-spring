package com.wuxp.security.example.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_demo")
@Entity
@Schema(description = "例子")
@Tag(name = "simple")
public class DemoEntity implements Serializable {

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
    @Size(max = 200)
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
