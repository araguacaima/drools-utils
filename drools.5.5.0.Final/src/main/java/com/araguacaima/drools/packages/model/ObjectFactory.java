package com.araguacaima.drools.packages.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.araguacaima.drools.packages.model package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BinaryLink_QNAME = new QName("", "binaryLink");
    private final static QName _Author_QNAME = new QName("", "author");
    private final static QName _Assets_QNAME = new QName("", "assets");
    private final static QName _Title_QNAME = new QName("", "title");
    private final static QName _CheckinComment_QNAME = new QName("", "checkinComment");
    private final static QName _Archived_QNAME = new QName("", "archived");
    private final static QName _Created_QNAME = new QName("", "created");
    private final static QName _Description_QNAME = new QName("", "description");
    private final static QName _SourceLink_QNAME = new QName("", "sourceLink");
    private final static QName _Uuid_QNAME = new QName("", "uuid");
    private final static QName _Published_QNAME = new QName("", "published");
    private final static QName _VersionNumber_QNAME = new QName("", "versionNumber");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.araguacaima.drools.packages.model
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Package }
     */
    public Package createPackage() {
        return new Package();
    }

    /**
     * Create an instance of {@link Collection }
     */
    public Collection createCollection() {
        return new Collection();
    }

    /**
     * Create an instance of {@link Metadata }
     */
    public Metadata createMetadata() {
        return new Metadata();
    }

    /**
     * Create an instance of {@link State }
     */
    public State createState() {
        return new State();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "binaryLink")
    public JAXBElement<String> createBinaryLink(String value) {
        return new JAXBElement<String>(_BinaryLink_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "author")
    public JAXBElement<String> createAuthor(String value) {
        return new JAXBElement<String>(_Author_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "assets")
    public JAXBElement<String> createAssets(String value) {
        return new JAXBElement<String>(_Assets_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "title")
    public JAXBElement<String> createTitle(String value) {
        return new JAXBElement<String>(_Title_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "checkinComment")
    public JAXBElement<String> createCheckinComment(String value) {
        return new JAXBElement<String>(_CheckinComment_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "archived")
    public JAXBElement<Boolean> createArchived(Boolean value) {
        return new JAXBElement<Boolean>(_Archived_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "created")
    public JAXBElement<XMLGregorianCalendar> createCreated(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_Created_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "description")
    public JAXBElement<String> createDescription(String value) {
        return new JAXBElement<String>(_Description_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "sourceLink")
    public JAXBElement<String> createSourceLink(String value) {
        return new JAXBElement<String>(_SourceLink_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "uuid")
    public JAXBElement<String> createUuid(String value) {
        return new JAXBElement<String>(_Uuid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "published")
    public JAXBElement<XMLGregorianCalendar> createPublished(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_Published_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "versionNumber")
    public JAXBElement<Byte> createVersionNumber(Byte value) {
        return new JAXBElement<Byte>(_VersionNumber_QNAME, Byte.class, null, value);
    }

}
