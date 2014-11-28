package com.araguacaima.drools.utils;

import com.bbva.utils.NotNullsLinkedHashSet;
import com.bbva.utils.templates.model.xls.BackendData;
import com.bbva.utils.templates.model.xls.InputAndOutputParameters;
import com.bbva.utils.templates.model.xls.MultiChannelServiceDetail;
import com.bbva.utils.templates.model.xls.Template;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.StatelessKieSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;


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
        droolsUtils = new DroolsUtils();
    }

    @Test
    public void executeRule() throws Exception {
        Collection<Object> assets = new ArrayList<Object>();
//        StatelessKieSession statelessKieSession = droolsUtils.getStatelessKieSession();
//        Assert.assertNotNull(statelessKieSession);
        Template template = new Template();
        NotNullsLinkedHashSet<MultiChannelServiceDetail> detalleServicios = new NotNullsLinkedHashSet<MultiChannelServiceDetail>(true);
        MultiChannelServiceDetail multiChannelServiceDetail = new MultiChannelServiceDetail();
        InputAndOutputParameters inputOutputParameter = new InputAndOutputParameters();
        NotNullsLinkedHashSet<BackendData> backEndDataList = new NotNullsLinkedHashSet<BackendData>(true);
        BackendData backEndData1 = new BackendData();
        backEndData1.setAccion("TEST");
        BackendData backEndData2 = new BackendData();
        backEndData2.setAccion("Crear");
        BackendData backEndData3 = new BackendData();
        backEndData3.setAccion("CREAR");
        backEndDataList.add(backEndData1);
        backEndDataList.add(backEndData2);
        backEndDataList.add(backEndData3);
        inputOutputParameter.setBackendData(backEndDataList);
        multiChannelServiceDetail.setParametrosDeEntradaYSalida(inputOutputParameter);
        detalleServicios.add(multiChannelServiceDetail);
        template.setDetalleServicios(detalleServicios);
    //    assets.add(template);
     //   assets.add(backEndDataList);
        assets.add(backEndData1);
        droolsUtils.runRulesEngineWithAssets(assets);
        Assert.assertNotNull(assets);
    }


}
