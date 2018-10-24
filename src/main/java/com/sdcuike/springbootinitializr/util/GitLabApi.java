package com.sdcuike.springbootinitializr.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.sdcuike.springbootinitializr.model.ProjectGroup;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

/**
 * gitlab操作api
 *
 * @author sdcuike
 * @date 2018/10/22
 * @since 2018/10/22
 */
public class GitLabApi {
    private String urlPrefix = "https://gitlab.com/api/v4/";


    private final String ROJECTURL_FORMAT = "git@gitlab.com:%s/%s.git";

    public static final String privateToken = "4KwekCdWeV5FoWKCfyxo";

    private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();


    /**
     *
     *
     * @return
     * @throws IOException
     */
    public List<ProjectGroup> getAllGroups() throws IOException {
        Request request = new Request.Builder().url(urlPrefix + "groups").get().build();

        String content = client.newCall(request).execute().body().string();
        return JSON.parseArray(content, ProjectGroup.class);
    }


    public String getProjectInfo(String namespace, String projectName) throws IOException {

        Request request = new Request.Builder().url(urlPrefix + "projects/" + URLEncoder.encode(namespace + "/" + projectName)).get().header("PRIVATE-TOKEN", privateToken).build();
        return client.newCall(request).execute().body().string();
    }

    /**
     *
     *
     * @param projectName
     * @param namespaceId
     * @return
     * @throws IOException
     */
    public String createProject(String projectName, String namespaceId) throws IOException {

        final FormBody.Builder builder = new FormBody.Builder().add("name", projectName);
        if (namespaceId != null && !namespaceId.trim().equals("")) {
            builder.add("namespace_id", namespaceId);
        }

        final FormBody formBody = builder.build();
        Request request = new Request.Builder().url(urlPrefix + "projects").post(formBody).build();

        return client.newCall(request).execute().body().string();

    }

    public String initProject(File projectRoot, String projectName, String nameSpace) throws GitAPIException, URISyntaxException {

        Preconditions.checkNotNull(projectRoot);
        Preconditions.checkArgument(!StringUtils.isEmptyOrNull(projectName));
        Preconditions.checkArgument(!StringUtils.isEmptyOrNull(nameSpace));

        try (Git git = Git.init().setDirectory(projectRoot).call()) {

            git.add().addFilepattern(".gitignore").call();
            git.add().addFilepattern("pom.xml").call();
            git.add().addFilepattern("src").call();
            git.add().addFilepattern("README.md").call();

            git.commit().setMessage("init project").call();

            String repositoryUrl = String.format(ROJECTURL_FORMAT, nameSpace, projectName);
            git.remoteAdd().setName("origin").setUri(new URIish(repositoryUrl)).call();
            git.push().setRemote("origin").setPushAll().call();

            return repositoryUrl;
        }
    }
}
