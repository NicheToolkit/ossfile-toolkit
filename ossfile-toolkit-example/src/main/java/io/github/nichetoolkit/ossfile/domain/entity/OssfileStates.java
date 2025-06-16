package io.github.nichetoolkit.ossfile.domain.entity;

import io.github.nichetoolkit.mybatis.table.RestAlertness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@RestAlertness
@NoArgsConstructor
@AllArgsConstructor
public class OssfileStates implements Serializable {
    protected Boolean partState;
    protected Boolean finishState;
    protected Boolean signatureState;
    protected Boolean compressState;
    protected Boolean previewState;

    public static OssfileStates ofPart(boolean part) {
        return OssfileStates.builder().partState(part).build();
    }

    public static OssfileStates ofFinish(boolean finish) {
        return OssfileStates.builder().finishState(finish).build();
    }

    public static OssfileStates ofSignature(boolean signature) {
        return OssfileStates.builder().signatureState(signature).build();
    }

    public static OssfileStates ofCompress(boolean compress) {
        return OssfileStates.builder().compressState(compress).build();
    }

    public static OssfileStates ofPreview(boolean preview) {
        return OssfileStates.builder().previewState(preview).build();
    }

}
