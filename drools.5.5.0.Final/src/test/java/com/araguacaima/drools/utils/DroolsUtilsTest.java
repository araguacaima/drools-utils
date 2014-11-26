package com.araguacaima.drools.utils;

import com.araguacaima.drools.DroolsAuthenticator;
import org.junit.Before;
import org.junit.Test;


/**
 * Pruebas sobre la clase utilitaria DroolsUtils <p>
 * Clase: DroolsUtilsTest.java <br>
 * Copyright: (c) 2013 Integra Consultores <br>
 * Company: Integra Consultores <br>
 *
 * @author Integra Consultores (INT)
 * @author Alejandro Méndez (AMM)
 *         <ul>
 *         <li> 2013-08-16 (AMM) Class creation. Method generateJarFromResourceDRLModel creted</li>
 *         </ul>
 */

public class DroolsUtilsTest {

    private DroolsUtils droolsUtils;

    @Before
    public void init() {
        DroolsAuthenticator droolsAuthenticator = new DroolsAuthenticator("admin", "admin");
        droolsUtils = new DroolsUtils(droolsAuthenticator);
    }


    @Test
    public void generateJarFromDRLModel() throws Exception {
        droolsUtils.generateJarFromResourceDRLModel("http://localhost:8080/drools-guvnor",
                "out\\drlModel");
    }

    @Test
    public void generateJarFromDRLModel2() throws Exception {
        droolsUtils.generateJarFromPackagedDRLModel("http://localhost:8080/drools-guvnor/org.drools.guvnor.Guvnor/package/cantv.ggto.centrodeservicios.operador/LATEST.drl",
                "out\\drlModel2");
    }



}
