package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.OssfileBulkModel;
import io.github.nichetoolkit.rest.RestException;

/**
 * <p>FileHandleService</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface FileHandleService {

    void autographImage(OssfileBulkModel fileIndex) throws RestException;

    void condenseImage(OssfileBulkModel fileIndex) throws RestException;

    void condenseFile(OssfileBulkModel fileIndex) throws RestException;
}
