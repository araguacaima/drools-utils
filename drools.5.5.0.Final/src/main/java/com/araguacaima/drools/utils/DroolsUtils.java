package com.araguacaima.drools.utils;

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.generic.ClassGen;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.FieldGen;
import com.sun.org.apache.bcel.internal.generic.Type;
import com.araguacaima.drools.DroolsAuthenticator;
import com.araguacaima.util.HttpUtil;
import com.araguacaima.util.JarUtil;
import com.araguacaima.util.StringUtil;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.PackageBuilder;
import org.drools.definition.KnowledgePackage;
import org.drools.definition.type.FactType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


/**
 * Clase utilitaria para manipular repositorio DRL de Jboss Drools <p>
 * Clase: DroolsUtils.java <br>
 *
 * @author Alejandro Manuel Méndez Araguacaima (AMMA)
 *         Changes:<br>
 *         <ul>
 *         <li> 2014-11-26 (AMMA)  Creacion de la clase. </li>
 *         </ul>
 */

public class DroolsUtils {

    protected final static String EOL = System.getProperty("line.separator");
    private static final Random rand = new Random();

    @SuppressWarnings("UnusedDeclaration")
    private DroolsUtils() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public DroolsUtils(String username, String password) {
        this(new DroolsAuthenticator(username, password));
    }

    @SuppressWarnings("UnusedDeclaration")
    public DroolsUtils(DroolsAuthenticator droolsAuthenticator) {
        Authenticator.setDefault(droolsAuthenticator);
    }

    public InputStream getDRLResurceAsXML(String source) throws Exception {
        return getDRLResurce(source, "application/xml", "UTF-8");
    }

    public ByteArrayInputStream getDRLResurce(String source, String contentType, String charset) throws Exception {
        HttpUtil httpUtil = new HttpUtil("localhost", 8080, "admin", "admin");
        final String content = httpUtil.getContent(source, null, "text/plain", "UTF-8", contentType, charset, HttpUtil.METHOD_TYPE.GET);
        return new ByteArrayInputStream(content.getBytes());
    }

    public void generateJarFromResourceDRLModel(String guvnorFullUrl, final String destFile) throws Exception {

        final String source = guvnorFullUrl + "/rest/packages";
        InputStream xmlInputStream = getDRLResurceAsXML(source);
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        try {
            com.araguacaima.drools.packages.model.Collection col;
            JAXBContext jc = JAXBContext.newInstance(com.araguacaima.drools.packages.model.Collection.class.getPackage().getName());
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            try {
                col = (com.araguacaima.drools.packages.model.Collection) unmarshaller.unmarshal(xmlInputStream);
                CollectionUtils.forAllDo(col.getPackage(), new Closure() {
                    @Override
                    public void execute(Object o) {
                        com.araguacaima.drools.packages.model.Package pack = (com.araguacaima.drools.packages.model.Package) o;
                        try {
                            CollectionUtils.forAllDo(pack.getAssets(), new Closure() {
                                @Override
                                public void execute(Object o) {
                                    String assetUrl = o + "/source";
                                    try {
                                        if (kbuilder.hasErrors()) {
                                            PackageBuilder packageBuilder = ((KnowledgeBuilderImpl) kbuilder).getPackageBuilder();
                                            Method resetProblems = packageBuilder.getClass().getDeclaredMethod("resetProblems");
                                            resetProblems.setAccessible(true);
                                            resetProblems.invoke(packageBuilder);
                                        }
                                        ByteArrayInputStream drlResurce = getDRLResurce(assetUrl, "text/plain", "UTF-8");
                                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                        String newPackage = StringUtils.difference(source, assetUrl);
                                        newPackage = newPackage.substring(1, newPackage.indexOf("/assets"));
                                        final String declaredPackage = "package " + newPackage.replaceAll("/", ".") + "\n\n";
                                        IOUtils.write(declaredPackage.getBytes(), outputStream);
                                        IOUtils.write(StringUtil.getStringFromInputStream(drlResurce), outputStream);
                                        if (declaredPackage.length() != outputStream.toByteArray().length) {
                                            drlResurce = new ByteArrayInputStream(outputStream.toByteArray());
                                            Resource r = ResourceFactory.newInputStreamResource(drlResurce);
                                            kbuilder.add(r, ResourceType.DRL);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } finally {
                IOUtils.closeQuietly(xmlInputStream);
            }
        } catch (javax.xml.bind.UnmarshalException ignored) {
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        if (kbuilder.hasErrors()) {
            PackageBuilder packageBuilder = ((KnowledgeBuilderImpl) kbuilder).getPackageBuilder();
            Method resetProblems = packageBuilder.getClass().getDeclaredMethod("resetProblems");
            resetProblems.setAccessible(true);
            resetProblems.invoke(packageBuilder);
        }
        packageKnowledge(kbuilder.getKnowledgePackages(), destFile, true);
    }

    public void generateJarFromPackagedDRLModel(String drlPackagedFullPath, final String destFile) throws Exception {

        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        try {
            URL url = new URL(drlPackagedFullPath);
            kbuilder.add(ResourceFactory.newUrlResource(url), ResourceType.DRL);

            // Check the builder for errors
            if (kbuilder.hasErrors()) {
                System.out.println(kbuilder.getErrors().toString());
                throw new RuntimeException("Unable to compile \"" + drlPackagedFullPath + "\".");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        packageKnowledge(kbuilder.getKnowledgePackages(), destFile, true);
    }

    private void packageKnowledge(Collection<KnowledgePackage> knowledgePackages, final String destFile, final boolean generatesNewSerialVersionUID) {
        CollectionUtils.forAllDo(knowledgePackages, new Closure() {
            @Override
            public void execute(Object o) {
                final KnowledgePackage knowledgePackage = (KnowledgePackage) o;
                final String fixedFolderName = StringUtils.replace(knowledgePackage.getName(), ".", File.separator);
                final String rootFolder = (knowledgePackage.getName().contains(".")) ? StringUtils.split(knowledgePackage.getName(), ".")[0] : knowledgePackage.getName();
                Collection<FactType> factTypes = knowledgePackage.getFactTypes();
                final Set<String> foldersToDelete = new TreeSet<String>();
                CollectionUtils.forAllDo(factTypes, new Closure() {
                    @Override
                    public void execute(Object o) {
                        FactType factType = (FactType) o;
                        try {
                            Class clazz = factType.newInstance().getClass();
                            final String className = clazz.getSimpleName();
                            final String file_name = destFile + File.separator + fixedFolderName + File.separator + className + ".class";
                            JavaClass BCELClazz = Repository.lookupClass(clazz);
                            ClassGen classGen = new ClassGen(BCELClazz);
                            ConstantPoolGen cPoolGen = classGen.getConstantPool();
                            FieldGen fieldGen = new FieldGen(Constants.ACC_PRIVATE | Constants.ACC_STATIC | Constants.ACC_FINAL, Type.LONG, "serialVersionUID", cPoolGen);
                            if (generatesNewSerialVersionUID) {
                                fieldGen.setInitValue(rand.nextLong());
                            } else {
                                fieldGen.setInitValue(new Long(1));
                            }
                            Field serialVersionUID = new Field(fieldGen.getField());
                            classGen.addField(serialVersionUID);
                            try {
                                classGen.getJavaClass().dump(file_name);
                                foldersToDelete.add(new File(destFile + File.separator + rootFolder).getPath());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    JarUtil.generateJarFromDirectory(destFile + File.separator + rootFolder, destFile + File.separator + knowledgePackage.getName() + ".jar", 0);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    for (String folderToDelete : foldersToDelete) {
                        try {
                            FileUtils.deleteDirectory(new File(folderToDelete));
                            foldersToDelete.remove(folderToDelete);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

    }

    @SuppressWarnings("UnusedDeclaration")
    public KnowledgeBase createKnowledgeBaseFromGuvnorAssets(KnowledgeBuilder kbuilder, String url, String username, String password, String pkg, String... assets) throws Exception {
        String changeSet = "<change-set xmlns='http://drools.org/drools-5.0/change-set'" + EOL +
                "            xmlns:xs='http://www.w3.org/2001/XMLSchema-instance'" + EOL +
                "            xs:schemaLocation='http://drools.org/drools-5.0/change-set http://anonsvn.jboss.org/repos/labs/labs/jbossrules/trunk/drools-api/src/main/resources/change-set-1.0.0.xsd' >" + EOL +
                "    <add>" + EOL;

        if (pkg == null || pkg.length() == 0) {
            String[] packages;
            List<String> resultPackages = getPackages(url);
            packages = new String[resultPackages.size()];
            resultPackages.toArray(packages);

            for (String p : packages) {
                List<String> resultAssets = getAssets(p, url);
                assets = new String[resultAssets.size()];
                resultAssets.toArray(assets);

                for (String a : assets) {
                    if (a.contains(".bpmn")) {
                        a = a.substring(0, a.indexOf(".bpmn"));
                    }
                    changeSet += "        <resource source='" + url + "/rest/packages/" + p + "/assets/" + a + "/binary' type='BPMN2' basicAuthentication=\"enabled\" username=\"" + username + "\" password=\"" + password + "\" />" + EOL;
                }
            }
        } else {
            if (assets == null || assets.length == 0 || (assets.length == 1 && assets[0] == null)) {
                List<String> resultAssets = getAssets(pkg, url);
                assets = new String[resultAssets.size()];
                resultAssets.toArray(assets);
            }
            for (String a : assets) {
                if (a.contains(".bpmn")) {
                    a = a.substring(0, a.indexOf(".bpmn"));
                }
                changeSet += "        <resource source='" + url + "/rest/packages/" + pkg + "/assets/" + a + "/binary' type='BPMN2' basicAuthentication=\"enabled\" username=\"" + username + "\" password=\"" + password + "\" />" + EOL;
            }
        }
        changeSet += "    </add>" + EOL +
                "</change-set>";
        kbuilder.add(ResourceFactory.newByteArrayResource(changeSet.getBytes()), ResourceType.CHANGE_SET);
        return kbuilder.newKnowledgeBase();
    }

    @SuppressWarnings("UnusedDeclaration")
    public KnowledgeBase createKnowledgeBaseFromGuvnorPackages(KnowledgeBuilder kbuilder, String url, String username, String password, String... packages) throws Exception {
        String changeSet = "<change-set xmlns='http://drools.org/drools-5.0/change-set'" + EOL +
                "            xmlns:xs='http://www.w3.org/2001/XMLSchema-instance'" + EOL +
                "            xs:schemaLocation='http://drools.org/drools-5.0/change-set http://anonsvn.jboss.org/repos/labs/labs/jbossrules/trunk/drools-api/src/main/resources/change-set-1.0.0.xsd' >" + EOL +
                "    <add>" + EOL;
        if (packages == null || packages.length == 0 || (packages.length == 1 && packages[0] == null)) {
            List<String> result = getPackages(url);
            packages = new String[result.size()];
            result.toArray(packages);
        }
        for (String p : packages) {
            changeSet += "        <resource source='" + url + "/rest/packages/" + p + "/binary' type='PKG' basicAuthentication=\"enabled\" username=\"" + username + "\" password=\"" + password + "\" />" + EOL;
        }
        changeSet += "    </add>" + EOL +
                "</change-set>";
        kbuilder.add(ResourceFactory.newByteArrayResource(changeSet.getBytes()), ResourceType.CHANGE_SET);
        return kbuilder.newKnowledgeBase();
    }

    public List<String> getPackages(String guvnorFullUrl) throws Exception {
        final List<String> result = new ArrayList<String>();
        final String source = guvnorFullUrl + "/rest/packages";
        InputStream xmlInputStream = this.getDRLResurceAsXML(source);
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        try {
            com.araguacaima.drools.packages.model.Collection col;
            JAXBContext jc = JAXBContext.newInstance(com.araguacaima.drools.packages.model.Collection.class.getPackage().getName());
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            try {
                col = (com.araguacaima.drools.packages.model.Collection) unmarshaller.unmarshal(xmlInputStream);
                CollectionUtils.forAllDo(col.getPackage(), new Closure() {
                    @Override
                    public void execute(Object o) {
                        com.araguacaima.drools.packages.model.Package pack = (com.araguacaima.drools.packages.model.Package) o;
                        result.add(pack.getTitle());
                    }
                });
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                IOUtils.closeQuietly(xmlInputStream);
            }
        } catch (javax.xml.bind.UnmarshalException ignored) {
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        if (kbuilder.hasErrors()) {
            PackageBuilder packageBuilder = ((KnowledgeBuilderImpl) kbuilder).getPackageBuilder();
            Method resetProblems = packageBuilder.getClass().getDeclaredMethod("resetProblems");
            resetProblems.setAccessible(true);
            resetProblems.invoke(packageBuilder);
        }
        return result;
    }


    public List<String> getAssets(final String packageName, String guvnorFullUrl) throws Exception {
        final List<String> result = new ArrayList<String>();
        final String source = guvnorFullUrl + "/rest/packages";
        InputStream xmlInputStream = this.getDRLResurceAsXML(source);
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        try {
            com.araguacaima.drools.packages.model.Collection col;
            JAXBContext jc = JAXBContext.newInstance(com.araguacaima.drools.packages.model.Collection.class.getPackage().getName());
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            try {
                col = (com.araguacaima.drools.packages.model.Collection) unmarshaller.unmarshal(xmlInputStream);
                CollectionUtils.forAllDo(col.getPackage(), new Closure() {
                    @Override
                    public void execute(Object o) {
                        com.araguacaima.drools.packages.model.Package pack = (com.araguacaima.drools.packages.model.Package) o;
                        if (pack.getTitle().equals(packageName)) {
                            CollectionUtils.forAllDo(pack.getAssets(), new Closure() {
                                @Override
                                public void execute(Object o) {
                                    result.add((String) o);
                                }
                            });
                        }
                    }
                });
            } finally {
                IOUtils.closeQuietly(xmlInputStream);
            }
        } catch (javax.xml.bind.UnmarshalException ignored) {
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        if (kbuilder.hasErrors()) {
            PackageBuilder packageBuilder = ((KnowledgeBuilderImpl) kbuilder).getPackageBuilder();
            Method resetProblems = packageBuilder.getClass().getDeclaredMethod("resetProblems");
            resetProblems.setAccessible(true);
            resetProblems.invoke(packageBuilder);
        }
        return result;
    }


}
