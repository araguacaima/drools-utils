package com.araguacaima.drools.utils;

import java.util.ResourceBundle;

/**
 * Created by Alejandro on 04/12/2014.
 */
public class DroolsConfig {

    private String kieSessionType;
    private String kieSession;
    private String protocol;
    private String server;
    private String port;
    private String appName;
    private String groupid;
    private String artifactid;
    private String version;
    private String url;

    public String getKieSessionType() {
        return kieSessionType;
    }

    public void setKieSessionType(String kieSessionType) {
        this.kieSessionType = kieSessionType;
    }

    public String getKieSession() {
        return kieSession;
    }

    public void setKieSession(String kieSession) {
        this.kieSession = kieSession;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getArtifactid() {
        return artifactid;
    }

    public void setArtifactid(String artifactid) {
        this.artifactid = artifactid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
