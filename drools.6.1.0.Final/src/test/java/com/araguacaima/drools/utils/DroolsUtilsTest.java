package com.araguacaima.drools.utils;

import com.bbva.utils.NotNullsLinkedHashSet;
import com.bbva.utils.templates.model.business.Comment;
import com.bbva.utils.templates.model.xls.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;


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
    public void testBackendDataRules() throws Exception {
        Collection<Object> assets = new ArrayList<Object>();

        final BackendData backEndData1 = new BackendData();
        backEndData1.setAccion("TEST");
        final BackendData backEndData2 = new BackendData();
        backEndData2.setAccion("Crear");
        final BackendData backEndData3 = new BackendData();
        backEndData3.setAccion("CREAR");
        assets.add(backEndData1);
        assets.add(backEndData2);
        assets.add(backEndData3);
        droolsUtils.runRulesEngineWithAssets(assets);
        Assert.assertNotNull(assets);
        Assert.assertEquals(5, assets.size());
        Collection<Comment> comments = (Collection<Comment>) CollectionUtils.select(assets, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return Comment.class.isAssignableFrom(o.getClass());
            }
        });
        Assert.assertEquals(2, comments.size());
        CollectionUtils.predicatedCollection(comments, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                Comment comment = (Comment) o;
                return comment.getActualValue().equals(backEndData1.getAccion())
                        || comment.getActualValue().equals(backEndData3.getAccion());
            }
        });

    }

    @Test
    public void testTemplate() throws Exception {
        Collection<Object> assets = new ArrayList<Object>();

        final BackendData backEndData1 = new BackendData();
        backEndData1.setAccion("TEST");
        final BackendData backEndData2 = new BackendData();
        backEndData2.setAccion("Crear");
        final BackendData backEndData3 = new BackendData();
        backEndData3.setAccion("CREAR");

        Template template = new Template();

        NotNullsLinkedHashSet<MultiChannelServiceDetail> detalleServicios = new NotNullsLinkedHashSet<MultiChannelServiceDetail>(true);
        MultiChannelServiceDetail multiChannelServiceDetail = new MultiChannelServiceDetail();
        InputAndOutputParameters inputOutputParameter = new InputAndOutputParameters();
        NotNullsLinkedHashSet<BackendData> backEndDataList = new NotNullsLinkedHashSet<BackendData>(true);
        backEndDataList.add(backEndData1);
        backEndDataList.add(backEndData2);
        backEndDataList.add(backEndData3);
        inputOutputParameter.setBackendData(backEndDataList);
        multiChannelServiceDetail.setParametrosDeEntradaYSalida(inputOutputParameter);
        TechnicalData technicalData = new TechnicalData();
        multiChannelServiceDetail.setDatosTecnicos(technicalData);
        NotNullsLinkedHashSet<Mapping> mappings = new NotNullsLinkedHashSet<Mapping>();
        Mapping mapping = new Mapping();
        mappings.add(mapping);
        multiChannelServiceDetail.setMappings(mappings);
        detalleServicios.add(multiChannelServiceDetail);
        template.setDetalleServicios(detalleServicios);
        assets.add(template);
        NotNullsLinkedHashSet<Entity> entidades = new NotNullsLinkedHashSet<Entity>();
        Entity entity = new Entity();
        entidades.add(entity);
        template.setEntidades(entidades);
        droolsUtils.runRulesEngineWithAssets(assets);
        Assert.assertNotNull(assets);
        Assert.assertEquals(12, assets.size());
        Collection<Comment> comments = (Collection<Comment>) CollectionUtils.select(assets, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return Comment.class.isAssignableFrom(o.getClass());
            }
        });
        Assert.assertEquals(2, comments.size());
        CollectionUtils.predicatedCollection(comments, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                Comment comment = (Comment) o;
                return comment.getActualValue().equals(backEndData1.getAccion())
                        || comment.getActualValue().equals(backEndData3.getAccion());
            }
        });
    }


}
