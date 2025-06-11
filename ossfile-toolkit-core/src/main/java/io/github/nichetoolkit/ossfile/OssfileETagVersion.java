package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileETagVersion implements Serializable {
    protected String etag;
    protected String version;

    public OssfileETagVersion() {
    }

    public OssfileETagVersion(String etag, String version) {
        this.etag = etag;
        this.version = version;
    }
}
