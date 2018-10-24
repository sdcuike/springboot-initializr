package com.sdcuike.springbootinitializr.util;

import com.sdcuike.springbootinitializr.dto.GenerateRequestDto;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sdcuike
 * @date 2018/10/22
 * @since 2018/10/22
 */

public class ProjectGenerator {

    private String tmpdir = ".";

    private TemplateRenderer templateRenderer;

    private GitLabApi gitLabApi;

    public ProjectGenerator(TemplateRenderer templateRenderer, GitLabApi gitLabApi) {
        this.templateRenderer = templateRenderer;
        this.gitLabApi = gitLabApi;
    }

    /**
     * 返回仓库地址
     *
     * @param requestDto
     * @return
     * @throws GitAPIException
     * @throws URISyntaxException
     */
    public String generateProject(GenerateRequestDto requestDto) throws GitAPIException, URISyntaxException, IOException {

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");

        Path path = Paths.get(tmpdir, dateTimeFormatter.format(localDateTime), requestDto.getArtifactId());

        final File file = path.toFile();

        file.mkdirs();

        generateGitIgnore(file);
        generateReadmine(file, requestDto);
        generateMavenPom(file, requestDto);

        generateSrcFiles(file, requestDto);

        generateResources(file);


        gitLabApi.createProject(requestDto.getArtifactId(), requestDto.getGitNameSpace().getId().toString());

        String repositoryUrl = gitLabApi.initProject(file, requestDto.getArtifactId(), requestDto.getGitNameSpace().getName());

        FileUtils.deleteDirectory(file);

        return repositoryUrl;
    }


    private void generateReadmine(File dir, GenerateRequestDto requestDto) {
        Map<String, Object> model = new LinkedHashMap<>();

        model.put("groupId", requestDto.getGroupId());
        model.put("artifactId", requestDto.getArtifactId());
        write(new File(dir, "README.md"), "README.md", model);

    }

    protected void generateGitIgnore(File dir) {
        Map<String, Object> model = new LinkedHashMap<>();

        model.put("build", "maven");
        model.put("mavenBuild", true);

        write(new File(dir, ".gitignore"), "gitignore.tmpl", model);
    }

    private void generateMavenPom(File dir, GenerateRequestDto requestDto) {
        Map<String, Object> model = new HashMap<>();
        model.put("groupId", requestDto.getGroupId());
        model.put("artifactId", requestDto.getArtifactId());
        model.put("name", requestDto.getName());
        model.put("description", requestDto.getDescription());

        model.put("dependencies", requestDto.getDependencies());


        String pom = new String(doGenerateMavenPom(model));
        writeText(new File(dir, "pom.xml"), pom);
    }


    private void generateSrcFiles(File root, GenerateRequestDto requestDto) {
        final String path = requestDto.getPackageName().replace(".", "/");
        File src = new File(root, "src/main/java/" + path);
        src.mkdirs();

        Map<String, Object> model = new HashMap<>();
        model.put("packageName", requestDto.getPackageName());

        write(new File(src, "Application.java"),
                "Application.java", model);


    }

    private void generateResources(File dir) {
        File resources = new File(dir, "src/main/resources");
        resources.mkdirs();
        writeText(new File(resources, "application.properties"), "server.port=8080");
    }

    private byte[] doGenerateMavenPom(Map<String, Object> model) {
        return this.templateRenderer.process("starter-pom.xml", model).getBytes();
    }


    public void write(File target, String templateName, Map<String, Object> model) {
        String body = this.templateRenderer.process(templateName, model);
        writeText(target, body);
    }

    private void writeText(File target, String body) {
        try (OutputStream stream = new FileOutputStream(target)) {
            StreamUtils.copy(body, Charset.forName("UTF-8"), stream);
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot write file " + target, ex);
        }
    }

}
