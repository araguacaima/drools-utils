package com.araguacaima.drools.utils;

import com.araguacaima.drools.utils.decorator.DroolsWorkbenchDecorator;
import com.araguacaima.drools.utils.factory.KieSessionImpl;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


/**
 * Clase utilitaria para manipular repositorio DRL de Jboss Drools <p>
 * Clase: DroolsUtils.java <br>
 *
 * @author Alejandro Manuel Méndez Araguacaima (AMMA)
 *         Changes:<br>
 *         <ul>
 *         <li> 2014-11-26 (AMMA)  Creacion de la clase. </li>
 *         </ul>
 */

public class DroolsUtils {

    private static ResourceBundle properties;
    private final String kieSessionType;
    private final String kieSession;
    private final String protocol;
    private final String server;
    private final String port;
    private final String appName;
    private final String groupid;
    private final String artifactid;
    private final String version;
    private final String url;

    public DroolsUtils(InputStream stream) throws IOException {
        properties = new PropertyResourceBundle(stream);
        this.protocol = properties.getString("jboos.drools.workbench.server.protocol");
        this.server = properties.getString("jboos.drools.workbench.server.name");
        this.port = properties.getString("jboos.drools.workbench.server.port");
        this.appName = properties.getString("jboos.drools.workbench.app.name");
        this.groupid = properties.getString("jboss.drools.workbench.maven.groupid");
        this.artifactid = properties.getString("jboss.drools.workbench.maven.artifactid");
        this.version = properties.getString("jboss.drools.workbench.maven.version");
        this.kieSession = properties.getString("jboss.drools.workbench.kie.session");
        this.kieSessionType = properties.getString("jboss.drools.workbench.kie.session.type");
        url = DroolsWorkbenchDecorator.decorate(protocol, server, port, appName, groupid, artifactid, version);
    }

    public String getUrl() {
        return url;
    }

    public String getKieSessionType() {
        return kieSessionType;
    }

    public String getKieSession() {
        return kieSession;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getServer() {
        return server;
    }

    public String getPort() {
        return port;
    }

    public String getAppName() {
        return appName;
    }

    public String getGroupid() {
        return groupid;
    }

    public String getArtifactid() {
        return artifactid;
    }

    public String getVersion() {
        return version;
    }

    KieContainer getKieContainer() throws IOException {

        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(url);

        InputStream is = urlResource.getInputStream();
        KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));

        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
        KieScanner kieScanner = ks.newKieScanner(kContainer);
        kieScanner.start(50000L);
        return kContainer;
    }


    public void executeRules(Collection<Object> assets) throws Exception {
        KieSessionImpl kieSessionImpl = KieSessionFactory.getSession(this);
        kieSessionImpl.execute(assets);
    }
}
