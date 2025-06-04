package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rice.RestId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <code>OssfileRequest</code>
 * <p>The ossfile request class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Data
 * @see lombok.EqualsAndHashCode
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileRequest implements Serializable {
    /**
     * <code>user</code>
     * {@link io.github.nichetoolkit.rice.RestId} <p>The <code>user</code> field.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    protected RestId<String> user;
    /**
     * <code>project</code>
     * {@link io.github.nichetoolkit.rice.RestId} <p>The <code>project</code> field.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    protected RestId<String> project;
    /**
     * <code>bulk</code>
     * {@link io.github.nichetoolkit.rice.RestId} <p>The <code>bulk</code> field.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    protected RestId<String> bulk;
    /**
     * <code>name</code>
     * {@link java.lang.String} <p>The <code>name</code> field.</p>
     * @see java.lang.String
     */
    protected String name;
    /**
     * <code>alias</code>
     * {@link java.lang.String} <p>The <code>alias</code> field.</p>
     * @see java.lang.String
     */
    protected String alias;
    /**
     * <code>fileSize</code>
     * {@link java.lang.Long} <p>The <code>fileSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long fileSize;
    /**
     * <code>fileType</code>
     * {@link java.lang.Integer} <p>The <code>fileType</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer fileType;
    /**
     * <code>properties</code>
     * {@link java.lang.String} <p>The <code>properties</code> field.</p>
     * @see java.lang.String
     */
    protected String properties;
    /**
     * <code>width</code>
     * {@link java.lang.Integer} <p>The <code>width</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer width;
    /**
     * <code>height</code>
     * {@link java.lang.Integer} <p>The <code>height</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer height;
    /**
     * <code>slice</code>
     * {@link java.lang.Boolean} <p>The <code>slice</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean slice = false;
    /**
     * <code>sliceSize</code>
     * {@link java.lang.Integer} <p>The <code>sliceSize</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer sliceSize;
    /**
     * <code>signature</code>
     * {@link java.lang.Boolean} <p>The <code>signature</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean signature = false;
    /**
     * <code>merge</code>
     * {@link java.lang.Boolean} <p>The <code>merge</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean merge = false;
    /**
     * <code>compress</code>
     * {@link java.lang.Boolean} <p>The <code>compress</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean compress = true;

}
