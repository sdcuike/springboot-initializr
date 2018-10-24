package com.sdcuike.springbootinitializr.dto;

import com.sdcuike.springbootinitializr.model.ProjectGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sdcuike
 * @date 2018/10/19
 * @since 2018/10/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitNameSpace {

    private String name;
    private Integer id;


    public static GitNameSpace of(ProjectGroup projectGroup) {
        return new GitNameSpace(projectGroup.getName(), projectGroup.getId());
    }
}
