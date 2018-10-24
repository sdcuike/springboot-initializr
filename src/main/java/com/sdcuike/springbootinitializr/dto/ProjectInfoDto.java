package com.sdcuike.springbootinitializr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目信息
 *
 * @author sdcuike
 * @date 2018/10/19
 * @since 2018/10/19
 */
@Data
public class ProjectInfoDto {

    /**
     * git 仓库空间列表
     */
    private List<GitNameSpace> gitNameSpaces = new ArrayList<>();

    /**
     * maven配置
     */
    private String groupId = "com.sdcuike";
    private String artifactId = "demo";
    private String name = "demo";
    private String description = "Demo project for Spring Boot";

    /**
     * 项目包名
     */
    private String packageName = groupId + "." + name;


    /**
     * 项目依赖
     */
    private List<DependencyInfo> dependencieList;


    /**
     * 项目依赖信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DependencyInfo {
        /**
         * 依赖类型，分类
         * @see DependencyType#name
         */
        private String type;
        private List<DependencyDto> dependencys;
    }
}
