package io.jpom.entity;

/**
 * @author bwcx_jzy
 * @date 2019/11/19
 */
public class NodeProjectInfo extends ProjectInfo {

    private String nodeId;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public NodeProjectInfo(ProjectInfo projectInfo) {
        this.copy(projectInfo);
    }

    public void copy(ProjectInfo projectInfo) {
        if (super.getId() == null) {
            super.setId(projectInfo.getId());
        }
        if (super.getName() == null) {
            super.setName(projectInfo.getName());
        }
        if (getGroup() == null) {
            super.setGroup(projectInfo.getGroup());
        }
        if (getArgs() == null) {
            super.setArgs(projectInfo.getArgs());
        }
        if (getWhitelistDirectory() == null) {
            super.setWhitelistDirectory(projectInfo.getWhitelistDirectory());
        }
        if (getPath() == null) {
            super.setPath(projectInfo.getPath());
        }
        if (getJvm() == null) {
            super.setJvm(projectInfo.getJvm());
        }
        if (getMainClass() == null) {
            super.setMainClass(projectInfo.getMainClass());
        }
        if (getRunMode() == null) {
            super.setRunMode(projectInfo.getRunMode());
        }
        if (getWebHook() == null) {
            super.setWebHook(projectInfo.getWebHook());
        }
    }

    public NodeProjectInfo() {
    }
}
