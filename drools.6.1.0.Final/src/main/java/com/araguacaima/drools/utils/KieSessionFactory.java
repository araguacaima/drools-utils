package com.araguacaima.drools.utils;

import com.araguacaima.drools.utils.factory.KieSessionImpl;
import com.araguacaima.drools.utils.factory.KieStatefulSessionImpl;
import com.araguacaima.drools.utils.factory.KieStatelessSessionImpl;
import org.kie.api.runtime.KieContainer;

import java.io.IOException;

/**
 * Created by Alejandro on 01/12/2014.
 */
public class KieSessionFactory {
    public static KieSessionImpl getSession(DroolsUtils droolsUtils) throws IOException {
        KieContainer kieContainer = droolsUtils.getKieContainer();
        if ("STATEFUL".equalsIgnoreCase(droolsUtils.getKieSessionType())) {
            return new KieStatefulSessionImpl(kieContainer.newKieSession(droolsUtils.getKieSession()));
        } else if ("STATELESS".equalsIgnoreCase(droolsUtils.getKieSessionType())) {
            return new KieStatelessSessionImpl(kieContainer.newStatelessKieSession(droolsUtils.getKieSession()));
        } else {
            throw new IllegalArgumentException("Kie Session's can be only of types STATEFUL and STATELESS");
        }
    }
}
