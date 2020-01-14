package com.oak.admin.services.infoprovide.info;

import com.levin.commons.dao.annotation.Ignore;
import com.levin.commons.service.domain.Desc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * Area
 * 2020-1-14 17:54:42
 */
@Schema(description = "Area")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"parent"})
public class AreaInfo implements Serializable {

    private static final long serialVersionUID = 3780293294960860167L;

    @Schema(description = "编码")
    @NotNull
    private String id;

    @Schema(description = "名称")
    @NotNull
    private String name;

    @Schema(description = "简称")
    @NotNull
    private String shortName;

    @Schema(description = "全称")
    private String fullName;

    @Schema(description = "上级ID")
    private String parentId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "深度，从1开始")
    private Integer level;

    @Schema(description = "省直区县")
    private Boolean directly = false;

    @Schema(description = "启用状态")
    private Boolean status;

    @Schema(description = "第三方地区")
    private String thirdCode;

    @Schema(description = "城市的")
    private Boolean urban;

    @Desc(value = "", code = "parent")
    @Schema(description = "上级地区")
    private AreaInfo parent;

    @Desc(value = "", code = "children")
    @Schema(description = "下级地区列表")
    private List<AreaInfo> children;


    public AreaInfo(String id) {
        this.id = id;
    }

    public Boolean getUrban() {
        return level == 2 || Boolean.TRUE.equals(urban);
    }

    public String getFullName() {
        return fullName == null ? name : fullName;
    }

    /**
     * 是否直辖市
     *
     * @return
     */
    public boolean isCities() {
        if (id == null) {
            return false;
        }

        return id.startsWith("11") || id.startsWith("12") || id.startsWith("31") || id.startsWith("50");
    }

    /**
     * 是否省
     *
     * @return
     */
    public boolean isProvincial() {
        return level != null && level == 1;
    }

    public String getCityCode() {
        if (id == null) {
            return null;
        }

        String cityAreaId = id.length() <= 6 ? id : id.substring(0, 6);

        return Boolean.TRUE.equals(directly) ? cityAreaId : (cityAreaId.length() <= 4 ? cityAreaId : cityAreaId.substring(0, 4));
    }


}
