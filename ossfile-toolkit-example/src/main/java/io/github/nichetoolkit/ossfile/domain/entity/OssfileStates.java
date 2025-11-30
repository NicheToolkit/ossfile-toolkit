package io.github.nichetoolkit.ossfile.domain.entity;

import io.github.nichetoolkit.mybatis.table.RestAlertness;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <code>OssfileStates</code>
 * <p>The ossfile states class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see io.github.nichetoolkit.mybatis.table.RestAlertness
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 * @since Jdk17
 */
@Getter
@Setter
@SuperBuilder
@RestAlertness
@NoArgsConstructor
@AllArgsConstructor
public class OssfileStates implements Serializable {
    /**
     * <code>partState</code>
     * {@link java.lang.Boolean} <p>The <code>partState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean partState;
    /**
     * <code>finishState</code>
     * {@link java.lang.Boolean} <p>The <code>finishState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean finishState;
    /**
     * <code>signatureState</code>
     * {@link java.lang.Boolean} <p>The <code>signatureState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean signatureState;
    /**
     * <code>compressState</code>
     * {@link java.lang.Boolean} <p>The <code>compressState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean compressState;
    /**
     * <code>previewState</code>
     * {@link java.lang.Boolean} <p>The <code>previewState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean previewState;

    /**
     * <code>ofPart</code>
     * <p>The of part method.</p>
     * @param part boolean <p>The part parameter is <code>boolean</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileStates} <p>The of part return object is <code>OssfileStates</code> type.</p>
     */
    public static OssfileStates ofPart(boolean part) {
        return OssfileStates.builder().partState(part).build();
    }

    /**
     * <code>ofFinish</code>
     * <p>The of finish method.</p>
     * @param finish boolean <p>The finish parameter is <code>boolean</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileStates} <p>The of finish return object is <code>OssfileStates</code> type.</p>
     */
    public static OssfileStates ofFinish(boolean finish) {
        return OssfileStates.builder().finishState(finish).build();
    }

    /**
     * <code>ofSignature</code>
     * <p>The of signature method.</p>
     * @param signature boolean <p>The signature parameter is <code>boolean</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileStates} <p>The of signature return object is <code>OssfileStates</code> type.</p>
     */
    public static OssfileStates ofSignature(boolean signature) {
        return OssfileStates.builder().signatureState(signature).build();
    }

    /**
     * <code>ofCompress</code>
     * <p>The of compress method.</p>
     * @param compress boolean <p>The compress parameter is <code>boolean</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileStates} <p>The of compress return object is <code>OssfileStates</code> type.</p>
     */
    public static OssfileStates ofCompress(boolean compress) {
        return OssfileStates.builder().compressState(compress).build();
    }

    /**
     * <code>ofPreview</code>
     * <p>The of preview method.</p>
     * @param preview boolean <p>The preview parameter is <code>boolean</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileStates} <p>The of preview return object is <code>OssfileStates</code> type.</p>
     */
    public static OssfileStates ofPreview(boolean preview) {
        return OssfileStates.builder().previewState(preview).build();
    }

}
