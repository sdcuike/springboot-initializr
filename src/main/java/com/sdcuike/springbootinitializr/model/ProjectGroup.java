package com.sdcuike.springbootinitializr.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * gitlab项目信息描述
 *
 * @author sdcuike
 * @date 2018/10/22
 * @since 2018/10/22
 */
@Data
@AllArgsConstructor
public class ProjectGroup {
    private int id;
    private String name;
    private String path;
}
