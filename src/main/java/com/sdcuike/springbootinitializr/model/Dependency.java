package com.sdcuike.springbootinitializr.model;

import lombok.Data;

/**
 * maven依赖配置，由于父pom引入，不必引入版本等信息
 *
 * @author sdcuike
 * @date 2018/10/19
 * @since 2018/10/19
 */
@Data
public class Dependency {
    protected String groupId;
    protected String artifactId;
}
