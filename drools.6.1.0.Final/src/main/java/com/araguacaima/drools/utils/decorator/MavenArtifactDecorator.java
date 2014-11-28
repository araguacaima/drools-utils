package com.araguacaima.drools.utils.decorator;

/**
 * Created by Alejandro on 28/11/2014.
 */
public class MavenArtifactDecorator {

    public static String decorateFullPath(String appName, String groupId, String artifactId, String version) {
        return "/"
                + appName
                + "/maven2"
                + decorateArtifactWithFullGroupPath(groupId, artifactId, version);
    }

    public static String decorateArtifactWithFullGroupPath(String groupId, String artifactId, String version) {
        return "/"
                + groupId.replaceAll("\\.", "/")
                + "/"
                + artifactId
                + "/"
                + version
                + decorateArtifact(artifactId, version);
    }

    public static String decorateArtifact(String artifactId, String version) {
        return "/"
                + artifactId
                + "-"
                + version
                + ".jar";
    }


}
