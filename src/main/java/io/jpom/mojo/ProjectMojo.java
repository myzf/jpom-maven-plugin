package io.jpom.mojo;

import io.jpom.entity.ProjectInfo;
import io.jpom.util.HttpUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bwcx_jzy
 * @date 2019/11/18
 */
@Mojo(name = "jpom-project", defaultPhase = LifecyclePhase.PACKAGE)
public class ProjectMojo extends AbstractMojo {

    @Parameter(required = true)
    private String url;

    @Parameter(required = true)
    private String token;

    @Parameter(required = true)
    private List<String> nodeIds;

    @Parameter
    private ProjectInfo project = new ProjectInfo();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (nodeIds == null || nodeIds.isEmpty()) {
            return;
        }

        // 验证参数
        String regex = "://(.*?):(.*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        if (!m.find()) {
            getLog().error("请配置 正确的Jpom Server地址");
            return;
        }
        String name = project.getName();
        if (name == null || "".equals(name)) {
            getLog().error("请配置 project.name");
            return;
        }

        String id = project.getId();
        if (id == null || "".equals(id)) {
            getLog().error("请配置 project.id");
            return;
        }

        String runMode = project.getRunMode();
        if (runMode == null || "".equals(runMode)) {
            getLog().error("请配置 project.runMode");
            return;
        }

        String whitelistDirectory = project.getWhitelistDirectory();
        if (whitelistDirectory == null || "".equals(whitelistDirectory)) {
            getLog().error("请配置 project.whitelistDirectory");
            return;
        }

        String path = project.getPath();
        if (path == null || "".equals(path)) {
            getLog().error("请配置 project.path");
            return;
        }

        Map<String, String> parameter = new HashMap<>(20);
        parameter.put("name", name);
        parameter.put("group", project.getGroup());
        parameter.put("id", id);
        parameter.put("runMode", runMode);
        parameter.put("whitelistDirectory", whitelistDirectory);
        parameter.put("lib", path);
        //
        parameter.put("mainClass", project.getMainClass());
        parameter.put("jvm", project.getJvm());
        parameter.put("args", project.getArgs());
        parameter.put("token", project.getWebHook());
        // header
        Map<String, String> header = new HashMap<>(5);
        // url
        String allUrl = String.format("%s/node/manage/saveProject", url);
        header.put("JPOM-USER-TOKEN", token);
        for (String nodeId : nodeIds) {
            parameter.put("nodeId", nodeId);
            getLog().info("处理：" + nodeId);
            String post = HttpUtils.post(allUrl, parameter, header,
                    (int) TimeUnit.MINUTES.toMillis(1),
                    (int) TimeUnit.MINUTES.toMillis(1), "utf-8");
            getLog().info(post);
        }
        getLog().info("处理结束");
    }


}
