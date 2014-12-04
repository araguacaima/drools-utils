package com.araguacaima.drools.utils;

import com.araguacaima.drools.utils.factory.KieSessionImpl;
import com.araguacaima.drools.utils.factory.KieStatefulSessionImpl;
import com.araguacaima.drools.utils.factory.KieStatelessSessionImpl;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.IOException;

/**
 * Created by Alejandro on 01/12/2014.
 */
public class KieSessionFactory {
    public static KieSessionImpl getSession(DroolsUtils droolsUtils) throws IOException {
        KieContainer kieContainer = droolsUtils.getKieContainer();
        final String kieSessionType = droolsUtils.getDroolsConfig().getKieSessionType();
        final String kieSession = droolsUtils.getDroolsConfig().getKieSession();
        if ("STATEFUL".equalsIgnoreCase(kieSessionType)) {
            return new KieStatefulSessionImpl(kieContainer.newKieSession(kieSession));
        } else if ("STATELESS".equalsIgnoreCase(kieSessionType)) {
            return new KieStatelessSessionImpl(kieContainer.newStatelessKieSession(kieSession));
        } else {
            throw new IllegalArgumentException("Kie Session's can be only of types STATEFUL and STATELESS");
        }
    }
}
