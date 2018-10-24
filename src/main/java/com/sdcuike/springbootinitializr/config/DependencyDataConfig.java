package com.sdcuike.springbootinitializr.config;

import com.sdcuike.springbootinitializr.dto.DependencyDto;
import com.sdcuike.springbootinitializr.dto.DependencyType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 依赖maven静态数据，数据只保存必要的，需要结合父pom，版本不允许自动
 *
 * @author sdcuike
 * @date 2018/10/23
 * @since 2018/10/23
 */

public final class DependencyDataConfig {


    private static Map<String/**类型 **/, List<DependencyDto>> dependencyDtos;

    private static Map<String/**别名 **/, DependencyDto> alias2dependency = new HashMap<>();


    public static Map<String, List<DependencyDto>> getDependencyDtos() {

        Map<String, List<DependencyDto>> dependencys = new HashMap<>();
        for (Map.Entry<String, List<DependencyDto>> entry : dependencyDtos.entrySet()) {
            dependencys.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return dependencys;
    }


    public static DependencyDto valueOfAlias(String alias) {
        return alias2dependency.get(alias);
    }

    static {
        List<DependencyDto> dependencyDtoList = Arrays.asList(
                new DependencyDto(DependencyType.Core, "lombok", "代码自动生成工具", "org.projectlombok", "lombok"));

        for (DependencyDto dependencyDto : dependencyDtoList) {
            alias2dependency.put(dependencyDto.getAlias(), dependencyDto);
        }

        dependencyDtos = dependencyDtoList.stream().collect(Collectors.groupingBy(t -> t.getType().getName()));


    }
}
