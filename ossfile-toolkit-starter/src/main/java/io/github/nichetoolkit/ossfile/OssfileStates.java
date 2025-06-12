package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.mybatis.table.RestAlertness;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@RestAlertness
public class OssfileStates implements Serializable {
    protected Boolean partState;
    protected Boolean finishState;
    protected Boolean compressState;
    protected Boolean previewState;

    public static OssfileStates ofPart(boolean part) {
        return OssfileStates.builder().partState(part).build();
    }

    public static OssfileStates ofFinish(boolean finish) {
        return OssfileStates.builder().finishState(finish).build();
    }
    public static OssfileStates ofCompress(boolean compress) {
        return OssfileStates.builder().compressState(compress).build();
    }

    public static OssfileStates ofPreview(boolean preview) {
        return OssfileStates.builder().previewState(preview).build();
    }

}
