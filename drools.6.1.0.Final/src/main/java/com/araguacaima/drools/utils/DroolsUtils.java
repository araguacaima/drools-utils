package com.araguacaima.drools.utils;

import com.araguacaima.drools.DroolsAuthenticator;
import com.araguacaima.drools.utils.decorator.DroolsWorkbenchDecorator;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import java.io.InputStream;
import java.util.Collection;
import java.util.Random;
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
    //    private DroolsAuthenticator droolsAuthenticator;
    protected final static String EOL = System.getProperty("line.separator");
    private static final Random rand = new Random();
    private String protocol;
    private String server;
    private String port;
    private String appName;
    private String groupid;
    private String artifactid;
    private String version;

    @SuppressWarnings("UnusedDeclaration")
    private DroolsUtils() {
        this.protocol = properties.getString("jboos.drools.workbench.server.protocol");
        this.server = properties.getString("jboos.drools.workbench.server.name");
        this.port = properties.getString("jboos.drools.workbench.server.port");
        this.appName = properties.getString("jboos.drools.workbench.app.name");
        this.groupid = properties.getString("jboss.drools.workbench.maven.groupid");
        this.artifactid = properties.getString("jboss.drools.workbench.maven.artifactid");
        this.version = properties.getString("jboss.drools.workbench.maven.version");
    }

    @SuppressWarnings("UnusedDeclaration")
    public DroolsUtils(String username, String password) {
        this(new DroolsAuthenticator(username, password));
    }

    @SuppressWarnings("UnusedDeclaration")
    public DroolsUtils(DroolsAuthenticator droolsAuthenticator) {
        this();
/*        this.droolsAuthenticator = droolsAuthenticator;
        Authenticator.setDefault(this.droolsAuthenticator);*/
    }

/*    public InputStream getArtifact() throws Exception {
        return getResurce(MavenArtifactDecorator.decorateArtifactWithFullGroupPath(
                        properties.getString("jboos.drools.workbench.app.name"),
                        properties.getString("jboss.drools.workbench.maven.groupid"),
                        properties.getString("jboss.drools.workbench.maven.artifactid"),
                        properties.getString("jboss.drools.workbench.maven.version")),
                "application/java-archive",
                "UTF-8");
    }

    public InputStream getResurce(String source, String contentType, String charset) throws Exception {
        HttpUtil httpUtil = new HttpUtil(properties.getString("jboos.drools.workbench.server.name"),
                Integer.valueOf(properties.getString("jboos.drools.workbench.server.port")),
                droolsAuthenticator.getUsername(),
                droolsAuthenticator.getPassword());
        return httpUtil.getContentAsStream(source, null, "text/plain", "UTF-8", contentType, charset, HttpUtil.METHOD_TYPE.GET);
    }
*/

    public StatelessKieSession getStatelessKieSession() throws Exception {
        String url = DroolsWorkbenchDecorator.decorate(protocol, server, port, appName, groupid, artifactid, version);
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();

        UrlResource urlResource = (UrlResource) ks.getResources()
                .newUrlResource(url);
//        urlResource.setUsername(properties.getString("jboss.drools.workbench.user"));
//        urlResource.setPassword(properties.getString("jboss.drools.workbench.password"));
//        urlResource.setBasicAuthentication("enabled");
        InputStream is = urlResource.getInputStream();

        KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
        return kContainer.newStatelessKieSession();
    }

    public void runRulesEngineWithAssets(Collection<Object> assets) throws Exception {
        StatelessKieSession kSession = getStatelessKieSession();
        kSession.execute(assets);
    }
}
