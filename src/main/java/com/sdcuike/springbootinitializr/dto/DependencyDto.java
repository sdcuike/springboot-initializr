package com.sdcuike.springbootinitializr.dto;

import com.sdcuike.springbootinitializr.model.Dependency;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author sdcuike
 * @date 2018/10/19
 * @since 2018/10/19
 */
@Data
@AllArgsConstructor
public class DependencyDto extends Dependency {
    /**
     * 依赖类型，前端展示，如core，web，
     */
    private DependencyType type;
    /**
     * 别名
     */
    private String alias;

    /**
     * 描述
     */
    private String description;


    public DependencyDto(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public DependencyDto(DependencyType type, String alias, String description, String groupId, String artifactId) {
        this.type = type;
        this.alias = alias;
        this.description = description;
        this.groupId = groupId;
        this.artifactId = artifactId;
    }


}