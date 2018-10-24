package com.sdcuike.springbootinitializr.controller;

import com.sdcuike.springbootinitializr.config.DependencyDataConfig;
import com.sdcuike.springbootinitializr.dto.DependencyDto;
import com.sdcuike.springbootinitializr.dto.GenerateRequestDto;
import com.sdcuike.springbootinitializr.dto.GitNameSpace;
import com.sdcuike.springbootinitializr.dto.ProjectInfoDto;
import com.sdcuike.springbootinitializr.util.GitLabApi;
import com.sdcuike.springbootinitializr.util.ProjectGenerator;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author sdcuike
 * @date 2018/10/19
 * @since 2018/10/19
 */
@Controller
public class IndexController {

    @Autowired
    private GitLabApi gitLabApi;

    @Autowired
    private ProjectGenerator projectGenerator;

    @GetMapping("/")
    public ModelAndView showInfo(Map<String, Object> model) throws IOException {
        ProjectInfoDto projectInfoDto = new ProjectInfoDto();

        List<GitNameSpace> gitNameSpaces = gitLabApi.getAllGroups().stream().map(GitNameSpace::of).collect(toList());
        projectInfoDto.setGitNameSpaces(gitNameSpaces);

        Map<String, List<DependencyDto>> dependencyDtos = DependencyDataConfig.getDependencyDtos();
        List<ProjectInfoDto.DependencyInfo> dependencieList = new ArrayList<>();
        for (Map.Entry<String, List<DependencyDto>> entry : dependencyDtos.entrySet()) {
            ProjectInfoDto.DependencyInfo dependencyInfo = new ProjectInfoDto.DependencyInfo(entry.getKey(), entry.getValue());
            dependencieList.add(dependencyInfo);
        }
        projectInfoDto.setDependencieList(dependencieList);

        model.put("projectInfo", projectInfoDto);
        return new ModelAndView("index", model);
    }


    @PostMapping("/generate")
    @ResponseBody
    public String generate(@RequestBody GenerateRequestDto requestDto) throws GitAPIException, IOException, URISyntaxException {
        List<DependencyDto> dependencies = requestDto.getDependencies();
        for (DependencyDto dependencyDto : dependencies) {
            DependencyDto dataFrom = DependencyDataConfig.valueOfAlias(dependencyDto.getAlias());
            dependencyDto.setGroupId(dataFrom.getGroupId());
            dependencyDto.setArtifactId(dataFrom.getArtifactId());
        }

        String projectInfo = gitLabApi.getProjectInfo(requestDto.getGitNameSpace().getName(), requestDto.getArtifactId());
        if (projectInfo == null || projectInfo.contains("404")) {
            return projectGenerator.generateProject(requestDto);
        }

        return "项目已经存在";
    }

}
