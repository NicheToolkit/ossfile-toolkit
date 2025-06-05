package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfilePartETag implements Serializable {
    protected Integer partIndex;
    protected String partEtag;

    public OssfilePartETag() {
    }

    public OssfilePartETag(Integer partIndex, String partEtag) {
        this.partIndex = partIndex;
        this.partEtag = partEtag;
    }
}
