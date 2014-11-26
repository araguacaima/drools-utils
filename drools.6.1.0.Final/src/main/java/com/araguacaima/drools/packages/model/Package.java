package com.araguacaima.drools.packages.model;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}assets" maxOccurs="unbounded"/>
 *         &lt;element ref="{}author"/>
 *         &lt;element ref="{}binaryLink"/>
 *         &lt;element ref="{}description"/>
 *         &lt;element ref="{}metadata"/>
 *         &lt;element ref="{}published"/>
 *         &lt;element ref="{}sourceLink"/>
 *         &lt;element ref="{}title"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "assets",
        "author",
        "binaryLink",
        "description",
        "metadata",
        "published",
        "sourceLink",
        "title"
})
@XmlRootElement(name = "package")
public class Package {

    @XmlElement(required = true)
    protected List<String> assets;
    @XmlElement(required = true)
    protected String author;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String binaryLink;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected Metadata metadata;
    @XmlElement(required = true)
    protected XMLGregorianCalendar published;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String sourceLink;
    @XmlElement(required = true)
    protected String title;

    /**
     * Gets the value of the assets property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assets property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssets().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getAssets() {
        if (assets == null) {
            assets = new ArrayList<String>();
        }
        return this.assets;
    }

    /**
     * Gets the value of the author property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Gets the value of the binaryLink property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBinaryLink() {
        return binaryLink;
    }

    /**
     * Sets the value of the binaryLink property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBinaryLink(String value) {
        this.binaryLink = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the metadata property.
     *
     * @return possible object is
     * {@link Metadata }
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     *
     * @param value allowed object is
     *              {@link Metadata }
     */
    public void setMetadata(Metadata value) {
        this.metadata = value;
    }

    /**
     * Gets the value of the published property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getPublished() {
        return published;
    }

    /**
     * Sets the value of the published property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setPublished(XMLGregorianCalendar value) {
        this.published = value;
    }

    /**
     * Gets the value of the sourceLink property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSourceLink() {
        return sourceLink;
    }

    /**
     * Sets the value of the sourceLink property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSourceLink(String value) {
        this.sourceLink = value;
    }

    /**
     * Gets the value of the title property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
