package com.araguacaima.drools.utils.factory;

import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;

import java.util.Collection;

/**
 * Created by Alejandro on 01/12/2014.
 */
public class KieStatefulSessionImpl implements KieSessionImpl {
    private final KieSession statefullSession;

    public KieStatefulSessionImpl(KieSession kieSession) {
        this.statefullSession = kieSession;
        statefullSession.addEventListener(new DebugAgendaEventListener());
        statefullSession.addEventListener(new org.drools.core.event.DebugAgendaEventListener());
        statefullSession.addEventListener(new DebugRuleRuntimeEventListener());
        statefullSession.addEventListener(new DefaultProcessEventListener());
    }

    @Override
    public void execute(Collection<Object> assets) {
        try {
            for (Object object : assets) {
                statefullSession.insert(object);
            }
            statefullSession.fireAllRules();
            assets.clear();
            assets.addAll(statefullSession.getObjects());
        } finally {
            statefullSession.dispose();
        }

    }
}
