package io.jpom.entity;

/**
 * 项目信息
 *
 * @author bwcx_jzy
 * @date 2019/11/18
 */
public class ProjectInfo {
    // 必传参数
    /**
     *
     */
    private String name;
    private String id;
    private String group;
    private String runMode;
    private String whitelistDirectory;
    private String path;

    // 非必传

    /**
     *
     */
    private String mainClass;
    private String jvm;
    private String args;
    private String webHook;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRunMode() {
        return runMode;
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
    }

    public String getWhitelistDirectory() {
        return whitelistDirectory;
    }

    public void setWhitelistDirectory(String whitelistDirectory) {
        this.whitelistDirectory = whitelistDirectory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getJvm() {
        return jvm;
    }

    public void setJvm(String jvm) {
        this.jvm = jvm;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getWebHook() {
        return webHook;
    }

    public void setWebHook(String webHook) {
        this.webHook = webHook;
    }

    @Override
    public String toString() {
        return "ProjectInfo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", group='" + group + '\'' +
                ", runMode='" + runMode + '\'' +
                ", whitelistDirectory='" + whitelistDirectory + '\'' +
                ", path='" + path + '\'' +
                ", mainClass='" + mainClass + '\'' +
                ", jvm='" + jvm + '\'' +
                ", args='" + args + '\'' +
                ", webHook='" + webHook + '\'' +
                '}';
    }
}
