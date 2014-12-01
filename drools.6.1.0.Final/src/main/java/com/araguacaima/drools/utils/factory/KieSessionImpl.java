package com.araguacaima.drools.utils.factory;

import java.util.Collection;

/**
 * Created by Alejandro on 01/12/2014.
 */
public interface KieSessionImpl {
    void execute(Collection<Object> assets);
}
