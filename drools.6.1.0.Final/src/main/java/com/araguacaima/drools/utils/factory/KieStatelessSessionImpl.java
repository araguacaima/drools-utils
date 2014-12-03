package com.araguacaima.drools.utils.factory;

import com.araguacaima.drools.utils.DroolsUtils;
import org.drools.core.impl.StatelessKnowledgeSessionImpl;
import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.Collection;

/**
 * Created by Alejandro on 01/12/2014.
 */
public class KieStatelessSessionImpl implements KieSessionImpl {
    private final StatelessKnowledgeSessionImpl statelessSession;

    public KieStatelessSessionImpl(StatelessKieSession statelessKieSession) {
        this.statelessSession = (StatelessKnowledgeSessionImpl) statelessKieSession;
        this.statelessSession.addEventListener(new DebugAgendaEventListener());
        this.statelessSession.addEventListener(new DebugRuleRuntimeEventListener());
        try {
            this.statelessSession.setGlobal("console", System.out);
        } catch (Throwable t) {
            System.out.println("No que posible asociar la consola por defecto (System.out) a la variable global " +
                    "\"console\". Por favor, valide que la misma exista y que está declarada como '"
                    + PrintStream.class.getName() + "'");
        }
        try {
            this.statelessSession.setGlobal("logger", LoggerFactory.getLogger(DroolsUtils.class));
        } catch (Throwable t) {
            System.out.println("No que posible asociar el Logger por defecto la variable global " +
                    "\"logger\". Por favor, valide que la misma exista y que está declarada como '"
                    + Logger.class.getName() + "'");
        }
    }

    @Override
    public void execute(Collection<Object> assets) {

        StatefulKnowledgeSession statefulKnowledgeSession = statelessSession.newWorkingMemory();
        try {
            for (Object object : assets) {
                if (Collection.class.isAssignableFrom(object.getClass())) {
                    execute((Collection<Object>) object);
                } else {
                    statefulKnowledgeSession.insert(object);
                }
            }
            statefulKnowledgeSession.fireAllRules();
            assets.clear();
            assets.addAll(statefulKnowledgeSession.getObjects());
        } finally {
            statefulKnowledgeSession.dispose();
        }
    }
}
