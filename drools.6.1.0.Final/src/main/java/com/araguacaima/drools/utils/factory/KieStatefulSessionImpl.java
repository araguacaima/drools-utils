package com.araguacaima.drools.utils.factory;

import com.araguacaima.drools.utils.DroolsUtils;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alejandro on 01/12/2014.
 */
public class KieStatefulSessionImpl implements KieSessionImpl {
    private final KieSession statefullSession;

    public KieStatefulSessionImpl(KieSession kieSession) {
        this.statefullSession = kieSession;
        this.statefullSession.addEventListener(new DebugAgendaEventListener());
        this.statefullSession.addEventListener(new DebugRuleRuntimeEventListener());
        try {
            this.statefullSession.setGlobal("console", System.out);
        } catch (Throwable t) {
            System.out.println("No que posible asociar la consola por defecto (System.out) a la variable global " +
                    "\"console\". Por favor, valide que la misma exista y que está declarada como '"
                    + PrintStream.class.getName() + "'");
        }
        try {
            this.statefullSession.setGlobal("logger", LoggerFactory.getLogger(DroolsUtils.class));
        } catch (Throwable t) {
            System.out.println("No que posible asociar el Logger por defecto la variable global " +
                    "\"logger\". Por favor, valide que la misma exista y que está declarada como '"
                    + Logger.class.getName() + "'");
        }
    }

    @Override
    public void execute(Collection<Object> assets) {
        try {
            for (Object object : assets) {
                if (Collection.class.isAssignableFrom(object.getClass())) {
                    execute((Collection<Object>) object);
                } else {
                    statefullSession.insert(object);
                }
            }
            statefullSession.fireAllRules();
            assets.clear();
            assets.addAll(statefullSession.getObjects());
        } finally {
            statefullSession.dispose();
        }
    }

    @Override
    public Collection<Object> execute(Object asset) {
        Collection<Object> assets = new ArrayList<Object>();
        try {

            if (Collection.class.isAssignableFrom(asset.getClass())) {
                execute((Collection<Object>) asset);
            } else {
                statefullSession.insert(asset);
            }

            statefullSession.fireAllRules();
            assets.addAll(statefullSession.getObjects());
        } finally {
            statefullSession.dispose();
        }
        return assets;
    }
}
