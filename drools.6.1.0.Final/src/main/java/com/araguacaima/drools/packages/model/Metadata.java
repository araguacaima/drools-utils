package com.araguacaima.drools.packages.model;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element ref="{}archived"/>
 *         &lt;element ref="{}checkinComment"/>
 *         &lt;element ref="{}created"/>
 *         &lt;element ref="{}state"/>
 *         &lt;element ref="{}uuid"/>
 *         &lt;element ref="{}versionNumber"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archived",
        "checkinComment",
        "created",
        "state",
        "uuid",
        "versionNumber"
})
@XmlRootElement(name = "metadata")
public class Metadata {

    protected boolean archived;
    @XmlElement(required = true)
    protected String checkinComment;
    @XmlElement(required = true)
    protected XMLGregorianCalendar created;
    @XmlElement(required = true)
    protected State state;
    @XmlElement(required = true)
    protected String uuid;
    protected byte versionNumber;

    /**
     * Gets the value of the archived property.
     */
    public boolean isArchived() {
        return archived;
    }

    /**
     * Sets the value of the archived property.
     */
    public void setArchived(boolean value) {
        this.archived = value;
    }

    /**
     * Gets the value of the checkinComment property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCheckinComment() {
        return checkinComment;
    }

    /**
     * Sets the value of the checkinComment property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCheckinComment(String value) {
        this.checkinComment = value;
    }

    /**
     * Gets the value of the created property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setCreated(XMLGregorianCalendar value) {
        this.created = value;
    }

    /**
     * Gets the value of the state property.
     *
     * @return possible object is
     * {@link State }
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     *
     * @param value allowed object is
     *              {@link State }
     */
    public void setState(State value) {
        this.state = value;
    }

    /**
     * Gets the value of the uuid property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the versionNumber property.
     */
    public byte getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the value of the versionNumber property.
     */
    public void setVersionNumber(byte value) {
        this.versionNumber = value;
    }

}
