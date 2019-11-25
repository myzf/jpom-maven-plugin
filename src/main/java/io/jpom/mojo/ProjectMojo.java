package io.jpom.mojo;

import io.jpom.entity.NodeProjectInfo;
import io.jpom.entity.ProjectInfo;
import io.jpom.util.HttpUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mojo
 *
 * @author myzf
 * @date 2019/11/18
 */
@Mojo(name = "yfcf-project", defaultPhase = LifecyclePhase.PACKAGE)
public class ProjectMojo extends AbstractMojo {

    @Parameter(required = true)
    private String url;

    @Parameter(required = true)
    private String token;

    @Parameter(required = true)
    private List<String> nodeIds;

    @Parameter
    private ProjectInfo project = new ProjectInfo();

    @Parameter
    private List<NodeProjectInfo> nodeProjects = new ArrayList<>();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // 验证参数
        String regex = "://(.*?):(.*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        if (!m.find()) {
            getLog().error("请配置 正确的Jpom Server地址");
            return;
        }
        if (nodeIds != null) {
            for (String nodeId : nodeIds) {
                NodeProjectInfo projectInfo = findItem(nodeId);
                if (projectInfo == null) {
                    projectInfo = new NodeProjectInfo(project);
                    projectInfo.setNodeId(nodeId);
                } else {
                    projectInfo.copy(project);
                }
                this.send(projectInfo);
            }
        }
        if (nodeProjects != null) {
            for (NodeProjectInfo nodeProject : nodeProjects) {
                this.send(nodeProject);
            }
        }
        getLog().info("处理结束");
    }

    private NodeProjectInfo findItem(String nodeId) {
        Iterator<NodeProjectInfo> iterator = nodeProjects.iterator();
        while (iterator.hasNext()) {
            NodeProjectInfo nodeProjectInfo = iterator.next();
            if (nodeId.equals(nodeProjectInfo.getNodeId())) {
                iterator.remove();
                return nodeProjectInfo;
            }
        }
        return null;
    }

    /**
     * 验证信息是否满足条件
     *
     * @param nodeProjectInfo info
     * @return true 满足
     */
    private boolean checkInfo(NodeProjectInfo nodeProjectInfo) {
        String name = nodeProjectInfo.getName();
        if (name == null || "".equals(name)) {
            getLog().error("请配置 project.name");
            return false;
        }

        String id = nodeProjectInfo.getId();
        if (id == null || "".equals(id)) {
            getLog().error("请配置 project.id");
            return false;
        }

        String runMode = nodeProjectInfo.getRunMode();
        if (runMode == null || "".equals(runMode)) {
            getLog().error("请配置 project.runMode");
            return false;
        }

        String whitelistDirectory = nodeProjectInfo.getWhitelistDirectory();
        if (whitelistDirectory == null || "".equals(whitelistDirectory)) {
            getLog().error("请配置 project.whitelistDirectory");
            return false;
        }

        String path = nodeProjectInfo.getPath();
        if (path == null || "".equals(path)) {
            getLog().error("请配置 project.path");
            return false;
        }
        return true;
    }

    /**
     * 发送同步信息请求
     *
     * @param nodeProjectInfo info
     */
    private void send(NodeProjectInfo nodeProjectInfo) {
        if (!checkInfo(nodeProjectInfo)) {
            return;
        }
        Map<String, String> parameter = new HashMap<>(20);
        parameter.put("name", nodeProjectInfo.getName());
        parameter.put("group", nodeProjectInfo.getGroup());
        parameter.put("id", nodeProjectInfo.getId());
        parameter.put("runMode", nodeProjectInfo.getRunMode());
        parameter.put("whitelistDirectory", nodeProjectInfo.getWhitelistDirectory());
        parameter.put("lib", nodeProjectInfo.getPath());
        //
        parameter.put("mainClass", nodeProjectInfo.getMainClass());
        parameter.put("jvm", nodeProjectInfo.getJvm());
        parameter.put("args", nodeProjectInfo.getArgs());
        parameter.put("token", nodeProjectInfo.getWebHook());
        // header
        Map<String, String> header = new HashMap<>(5);
        parameter.put("nodeId", nodeProjectInfo.getNodeId());
        // url
        String allUrl = String.format("%s/node/manage/saveProject", url);
        header.put("JPOM-USER-TOKEN", token);
        getLog().info("处理：" + nodeProjectInfo.getNodeId());
        String post = HttpUtils.post(allUrl, parameter, header,
                (int) TimeUnit.MINUTES.toMillis(1),
                (int) TimeUnit.MINUTES.toMillis(1), "utf-8");
        getLog().info(post);
    }
}
