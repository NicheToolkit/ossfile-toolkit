package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rice.DefaultIdModel;
import io.github.nichetoolkit.rice.RestId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

/**
 * <code>OssfilePartModel</code>
 * <p>The ossfile part model class.</p>
 * @param <M> {@link io.github.nichetoolkit.ossfile.OssfilePartModel} <p>The generic parameter is <code>OssfilePartModel</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.ossfile.OssfilePartEntity} <p>The generic parameter is <code>OssfilePartEntity</code> type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfilePartEntity
 * @see io.github.nichetoolkit.rice.DefaultIdModel
 * @see java.util.Comparator
 * @see java.lang.Comparable
 * @see io.github.nichetoolkit.ossfile.OssfileResource
 * @see lombok.Data
 * @see lombok.EqualsAndHashCode
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfilePartModel<M extends OssfilePartModel<M,E>,E extends OssfilePartEntity<E,M>> extends DefaultIdModel<M,E,String> implements Comparator<M>, Comparable<M>, OssfileResource {
    /**
     * <code>bulk</code>
     * {@link io.github.nichetoolkit.rice.RestId} <p>The <code>bulk</code> field.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    protected RestId<String> bulk;
    /**
     * <code>project</code>
     * {@link io.github.nichetoolkit.rice.RestId} <p>The <code>project</code> field.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    protected RestId<String> project;

    /**
     * <code>filename</code>
     * {@link java.lang.String} <p>The <code>filename</code> field.</p>
     * @see java.lang.String
     */
    protected String filename;
    /**
     * <code>partIndex</code>
     * {@link java.lang.Integer} <p>The <code>partIndex</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer partIndex;
    /**
     * <code>partSize</code>
     * {@link java.lang.Long} <p>The <code>partSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partSize;
    /**
     * <code>partStart</code>
     * {@link java.lang.Long} <p>The <code>partStart</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partStart;
    /**
     * <code>partEnd</code>
     * {@link java.lang.Long} <p>The <code>partEnd</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partEnd;
    /**
     * <code>partMd5</code>
     * {@link java.lang.String} <p>The <code>partMd5</code> field.</p>
     * @see java.lang.String
     */
    protected String partMd5;
    /**
     * <code>lastPart</code>
     * {@link java.lang.Boolean} <p>The <code>lastPart</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean lastPart;

    /**
     * <code>partTime</code>
     * {@link java.util.Date} <p>The <code>partTime</code> field.</p>
     * @see java.util.Date
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date partTime;
    /**
     * <code>startTime</code>
     * {@link java.util.Date} <p>The <code>startTime</code> field.</p>
     * @see java.util.Date
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date startTime;
    /**
     * <code>endTime</code>
     * {@link java.util.Date} <p>The <code>endTime</code> field.</p>
     * @see java.util.Date
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date endTime;

    /**
     * <code>bytes</code>
     * <p>The <code>bytes</code> field.</p>
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    protected byte[] bytes;

    /**
     * <code>inputStream</code>
     * <p>The input stream method.</p>
     * @return {@link java.io.ByteArrayInputStream} <p>The input stream return object is <code>ByteArrayInputStream</code> type.</p>
     * @see java.io.ByteArrayInputStream
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    public ByteArrayInputStream inputStream() {
        return new ByteArrayInputStream(this.bytes);
    }

    /**
     * <code>OssfilePartModel</code>
     * <p>Instantiates a new ossfile part model.</p>
     */
    public OssfilePartModel() {
    }

    /**
     * <code>OssfilePartModel</code>
     * <p>Instantiates a new ossfile part model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssfilePartModel(String id) {
        super(id);
    }

    @Override
    public int compare(OssfilePartModel source, OssfilePartModel target) {
        return Integer.compare(source.getPartIndex(), target.getPartIndex());
    }

    @Override
    public int compareTo(@NonNull OssfilePartModel target) {
        return Integer.compare(this.getPartIndex(), target.getPartIndex());
    }

    @Override
    public String getObjectKey() {
        return this.id;
    }

}
