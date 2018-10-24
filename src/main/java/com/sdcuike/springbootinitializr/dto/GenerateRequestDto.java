package com.sdcuike.springbootinitializr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sdcuike
 * @date 2018/10/23
 * @since 2018/10/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateRequestDto {
    /**
     * git 仓库空间
     */
    private GitNameSpace gitNameSpace;

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
    private List<DependencyDto> dependencies = new ArrayList<>();
}
