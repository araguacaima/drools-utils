package com.araguacaima.drools.utils.factory;

import org.drools.core.impl.StatelessKnowledgeSessionImpl;
import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.Collection;

/**
 * Created by Alejandro on 01/12/2014.
 */
public class KieStatelessSessionImpl implements KieSessionImpl {
    private final StatelessKnowledgeSessionImpl statelessSession;

    public KieStatelessSessionImpl(StatelessKieSession statelessKieSession) {
        this.statelessSession = (StatelessKnowledgeSessionImpl) statelessKieSession;
        statelessSession.addEventListener(new DebugAgendaEventListener());
        statelessSession.addEventListener(new org.drools.core.event.DebugAgendaEventListener());
        statelessSession.addEventListener(new DebugRuleRuntimeEventListener());
        statelessSession.addEventListener(new DefaultProcessEventListener());
    }

    @Override
    public void execute(Collection<Object> assets) {

        StatefulKnowledgeSession statefulKnowledgeSession = statelessSession.newWorkingMemory();
        try {
            for (Object object : assets) {
                statefulKnowledgeSession.insert(object);
            }
            statefulKnowledgeSession.fireAllRules();
            assets.clear();
            assets.addAll(statefulKnowledgeSession.getObjects());
        } finally {
            statefulKnowledgeSession.dispose();
        }
    }
}
