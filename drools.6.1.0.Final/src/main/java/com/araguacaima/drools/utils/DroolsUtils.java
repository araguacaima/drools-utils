package com.araguacaima.drools.utils;

import com.araguacaima.drools.utils.decorator.DroolsWorkbenchDecorator;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import java.io.InputStream;
import java.util.Collection;
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

    private static final ResourceBundle properties = ResourceBundle.getBundle("drools");
    private String protocol;
    private String server;
    private String port;
    private String appName;
    private String groupid;
    private String artifactid;
    private String version;

    @SuppressWarnings("UnusedDeclaration")
    public DroolsUtils() {
        this.protocol = properties.getString("jboos.drools.workbench.server.protocol");
        this.server = properties.getString("jboos.drools.workbench.server.name");
        this.port = properties.getString("jboos.drools.workbench.server.port");
        this.appName = properties.getString("jboos.drools.workbench.app.name");
        this.groupid = properties.getString("jboss.drools.workbench.maven.groupid");
        this.artifactid = properties.getString("jboss.drools.workbench.maven.artifactid");
        this.version = properties.getString("jboss.drools.workbench.maven.version");
    }

    public StatelessKieSession getStatelessKieSession() throws Exception {
        String url = DroolsWorkbenchDecorator.decorate(protocol, server, port, appName, groupid, artifactid, version);
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();

        UrlResource urlResource = (UrlResource) ks.getResources()
                .newUrlResource(url);
        InputStream is = urlResource.getInputStream();

        KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
        return kContainer.newStatelessKieSession();
    }

    public StatelessKieSession getStatelessKieSession2() throws Exception {
        String url = DroolsWorkbenchDecorator.decorate(protocol, server, port, appName, groupid, artifactid, version);
        // make sure you use "LATEST" here!
        ReleaseIdImpl releaseId = new ReleaseIdImpl(groupid, artifactid, version);
        KieServices ks = KieServices.Factory.get();
        ks.getResources().newUrlResource(url);
        KieContainer kieContainer = ks.newKieContainer(releaseId);
        return kieContainer.newStatelessKieSession();
    }

    public void runRulesEngineWithAssets(Collection<Object> assets) throws Exception {
        StatelessKieSession kSession = getStatelessKieSession2();
        kSession.execute(assets);
    }
}
