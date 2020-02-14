package com.wuxp.security.example.entities;


import com.levin.commons.dao.domain.support.AbstractBaseEntityObject;
import com.levin.commons.service.domain.Desc;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Schema(name = "example例子", description = "example例子")
@Table(name = "t_example")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Tag(name = "simple")
public class ExampleEntity extends AbstractBaseEntityObject<Long> {

    private static final long serialVersionUID = -123545031462572985L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    @NotNull
    @Size(max = 16)
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "年龄")
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

    @Schema(description = "创建日期")
    @Column(name = "week", nullable = false)
    @Enumerated(EnumType.STRING)
    private Week week;

    @Schema(description = "例子id")
    @Column(name = "example_id", nullable = false)
    private Long exampleId;

//    @Schema(description = "例子")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "example_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private ExampleEntity exampleEntity;

}
