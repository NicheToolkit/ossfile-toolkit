package io.github.nichetoolkit.ossfile.controller;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog;
import io.github.nichetoolkit.rest.util.FileUtils;
import io.github.nichetoolkit.rice.stereotype.RestSkip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * <code>PartController</code>
 * <p>The part controller class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see io.github.nichetoolkit.rice.stereotype.RestSkip
 * @see io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @since Jdk1.8
 */
@Slf4j
@RestSkip
@RestNotelog
@RestController
@RequestMapping("/ossfile/part")
public class PartController {

    /**
     * <code>sliceFile</code>
     * <p>The slice file method.</p>
     * @param file {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param size long <p>The size parameter is <code>long</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The slice file return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     * @see org.springframework.web.bind.annotation.RequestPart
     * @see org.springframework.web.bind.annotation.RequestParam
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/slice")
    public RestResult<?> sliceFile(@RequestPart("file") MultipartFile file,
                                   @RequestParam(value = "size", required = false, defaultValue = "0") long size) throws RestException {
        String originalFilename = file.getOriginalFilename();
        String filename = FileUtils.filename(originalFilename);
        String tempPath = "C:\\Users\\Snow\\Desktop\\test\\slice";
        try {
            String cachePath = FileUtils.createPath(tempPath);
            assert originalFilename != null;
            File originalFile = FileUtils.createFile(cachePath.concat(File.separator).concat(originalFilename));
            file.transferTo(originalFile);
            // 100Mb
            long partSize = size == 0 ? 100 * 1024 * 1024L : size >= 100 * 1024 * 1024L ? size : 1100 * 1024 * 1024L;
            long sliceSize = originalFile.length() / partSize;
            if (file.getSize() % partSize != 0) {
                sliceSize += 1;
            }
            RandomAccessFile accessFile = new RandomAccessFile(originalFile, "rwd");
            FileChannel accessFileChannel = accessFile.getChannel();
            for (int i = 1; i <= sliceSize; i++) {
                long position = (i - 1) * partSize;
                long positionEnd = position + partSize;
                if (i == sliceSize) {
                    positionEnd = file.getSize();
                }
                partSize = positionEnd - position;
                String sliceFilename = cachePath.concat(File.separator).concat(filename).concat("_").concat(String.valueOf(i))
                        .concat("_").concat(String.valueOf(partSize));
                File sliceFile = FileUtils.createFile(sliceFilename);
                RandomAccessFile sliceAccessFile = new RandomAccessFile(sliceFile, "rwd");
                FileChannel sliceAccessFileChannel = sliceAccessFile.getChannel();
                accessFileChannel.transferTo(position, partSize, sliceAccessFileChannel);
                log.debug("the file of {} slice success. path: {}", sliceFile.getName(), sliceFile.getPath());
                sliceAccessFileChannel.close();
            }
            accessFileChannel.close();
        } catch (IOException exception) {
            throw new ServiceErrorException(exception.getMessage());
        }
        return RestResult.success();
    }

}
