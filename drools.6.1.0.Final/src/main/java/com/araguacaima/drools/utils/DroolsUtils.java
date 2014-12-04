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

    private final DroolsConfig droolsConfig;
    private String url;

    public DroolsUtils(DroolsConfig droolsConfig) throws IOException {
        this.droolsConfig = droolsConfig;
        String protocol = droolsConfig.getProtocol();
        String server = droolsConfig.getServer();
        String port = droolsConfig.getPort();
        String appName = droolsConfig.getAppName();
        String groupid = droolsConfig.getGroupid();
        String artifactid = droolsConfig.getArtifactid();
        String version = droolsConfig.getVersion();
        String kieSession = droolsConfig.getKieSession();
        String kieSessionType = droolsConfig.getKieSessionType();
        this.url = DroolsWorkbenchDecorator.decorate(protocol, server, port, appName, groupid, artifactid, version);
    }

    public DroolsConfig getDroolsConfig() {
        return droolsConfig;
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

    public Collection<Object> executeRules(Object asset) throws Exception {
        KieSessionImpl kieSessionImpl = KieSessionFactory.getSession(this);
        return kieSessionImpl.execute(asset);
    }
}
