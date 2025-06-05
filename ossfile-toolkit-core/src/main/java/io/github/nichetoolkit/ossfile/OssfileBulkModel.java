package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.net.httpserver.Headers;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultInfoModel;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.jsonb.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <code>OssfileBulkModel</code>
 * <p>The ossfile bulk model class.</p>
 * @param <M> {@link io.github.nichetoolkit.ossfile.OssfileBulkModel} <p>The generic parameter is <code>OssfileBulkModel</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.ossfile.OssfileBulkEntity} <p>The generic parameter is <code>OssfileBulkEntity</code> type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileBulkEntity
 * @see io.github.nichetoolkit.rice.DefaultInfoModel
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
public abstract class OssfileBulkModel<M extends OssfileBulkModel<M, E>, E extends OssfileBulkEntity<E,M>> extends DefaultInfoModel<M, E, String> implements OssfileResource {
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
     * <code>original</code>
     * {@link java.lang.String} <p>The <code>original</code> field.</p>
     * @see java.lang.String
     */
    protected String original;
    /**
     * <code>filename</code>
     * {@link java.lang.String} <p>The <code>filename</code> field.</p>
     * @see java.lang.String
     */
    protected String filename;
    /**
     * <code>alias</code>
     * {@link java.lang.String} <p>The <code>alias</code> field.</p>
     * @see java.lang.String
     */
    protected String alias;
    /**
     * <code>suffix</code>
     * {@link java.lang.String} <p>The <code>suffix</code> field.</p>
     * @see java.lang.String
     */
    protected String suffix;

    /**
     * <code>fileMd5</code>
     * {@link java.lang.String} <p>The <code>fileMd5</code> field.</p>
     * @see java.lang.String
     */
    protected String fileMd5;
    /**
     * <code>fileSize</code>
     * {@link java.lang.Long} <p>The <code>fileSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long fileSize;
    /**
     * <code>fileType</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileFileType} <p>The <code>fileType</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileFileType
     */
    protected OssfileFileType fileType;
    /**
     * <code>properties</code>
     * {@link java.util.List} <p>The <code>properties</code> field.</p>
     * @see java.util.List
     */
    protected List<Property> properties;

    /**
     * <code>finish</code>
     * {@link java.lang.Boolean} <p>The <code>finish</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean finish;
    /**
     * <code>compress</code>
     * {@link java.lang.Boolean} <p>The <code>compress</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean compress;
    /**
     * <code>part</code>
     * {@link java.lang.Boolean} <p>The <code>part</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean part;
    /**
     * <code>parteSize</code>
     * {@link java.lang.Integer} <p>The <code>parteSize</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer parteSize;
    /**
     * <code>merge</code>
     * {@link java.lang.Boolean} <p>The <code>merge</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean merge;

    /**
     * <code>uploadId</code>
     * {@link java.lang.String} <p>The <code>uploadId</code> field.</p>
     * @see java.lang.String
     */
    protected String uploadId;

    /**
     * <code>etag</code>
     * {@link java.lang.String} <p>The <code>etag</code> field.</p>
     * @see java.lang.String
     */
    protected String etag;
    /**
     * <code>version</code>
     * {@link java.lang.String} <p>The <code>version</code> field.</p>
     * @see java.lang.String
     */
    protected String version;
    /**
     * <code>headers</code>
     * {@link java.util.Map} <p>The <code>headers</code> field.</p>
     * @see java.util.Map
     */
    protected Map<String, List<String>> headers;

    /**
     * <code>fileParts</code>
     * {@link java.util.List} <p>The <code>fileParts</code> field.</p>
     * @see java.util.List
     */
    protected List<OssfilePartModel<?,?>> fileParts;

    /**
     * <code>filePart</code>
     * {@link io.github.nichetoolkit.ossfile.OssfilePartModel} <p>The <code>filePart</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.OssfilePartModel
     */
    protected OssfilePartModel<?,?> filePart;

    /**
     * <code>bytes</code>
     * <p>The <code>bytes</code> field.</p>
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    protected byte[] bytes;

    /**
     * <code>file</code>
     * {@link java.io.File} <p>The <code>file</code> field.</p>
     * @see java.io.File
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    protected File file;

    @Override
    public String getObjectKey() {
        return this.id;
    }

    /**
     * <code>OssfileBulkModel</code>
     * <p>Instantiates a new ossfile bulk model.</p>
     */
    public OssfileBulkModel() {
    }

    /**
     * <code>OssfileBulkModel</code>
     * <p>Instantiates a new ossfile bulk model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssfileBulkModel(String id) {
        super(id);
    }

    @Override
    public boolean isReadable() {
        return this.finish;
    }

    /**
     * <code>ofRequest</code>
     * <p>The of request method.</p>
     * @param request {@link io.github.nichetoolkit.ossfile.OssfileRequest} <p>The request parameter is <code>OssfileRequest</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileBulkModel} <p>The of request return object is <code>OssfileBulkModel</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileRequest
     */
    abstract public OssfileBulkModel<?,?> ofRequest(OssfileRequest request);

    /**
     * <code>inputStream</code>
     * <p>The input stream method.</p>
     * @return {@link java.io.InputStream} <p>The input stream return object is <code>InputStream</code> type.</p>
     * @see java.io.InputStream
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    public InputStream inputStream() {
        if (GeneralUtils.isNotEmpty(this.bytes)) {
            return new ByteArrayInputStream(this.bytes);
        } else if (GeneralUtils.isNotEmpty(this.file)) {
            try {
                return Files.newInputStream(this.file.toPath());
            } catch (IOException ignored) {
            }
        }
        return null;
    }

    /**
     * <code>setAlias</code>
     * <p>The set alias setter method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setAlias(String alias) {
        this.alias = alias;
        addProperty("alias", alias);
    }

    /**
     * <code>setSuffix</code>
     * <p>The set suffix setter method.</p>
     * @param suffix {@link java.lang.String} <p>The suffix parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
        addProperty("suffix", suffix);
    }

    /**
     * <code>setFileMd5</code>
     * <p>The set file md 5 setter method.</p>
     * @param fileMd5 {@link java.lang.String} <p>The file md 5 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        addProperty("md5", fileMd5);
    }

    /**
     * <code>setFileSize</code>
     * <p>The set file size setter method.</p>
     * @param fileSize {@link java.lang.Long} <p>The file size parameter is <code>Long</code> type.</p>
     * @see java.lang.Long
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
        addProperty("size", fileSize);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        addProperty("name", name);
    }

    /**
     * <code>headers</code>
     * <p>The headers method.</p>
     * @param headers {@link com.sun.net.httpserver.Headers} <p>The headers parameter is <code>Headers</code> type.</p>
     * @see com.sun.net.httpserver.Headers
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    public void headers(Headers headers) {
        this.headers = headers;
    }

    /**
     * <code>addProperty</code>
     * <p>The add property method.</p>
     * @param name  {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.NonNull
     * @see java.lang.Object
     */
    public void addProperty(@NonNull String name, Object value) {
        if (GeneralUtils.isNotEmpty(value)) {
            if (GeneralUtils.isEmpty(this.properties)) {
                this.properties = new ArrayList<>();
            } else {
                this.properties.remove(new Property(name));
            }
            this.properties.add(new Property(name, value));
        }
    }

}
