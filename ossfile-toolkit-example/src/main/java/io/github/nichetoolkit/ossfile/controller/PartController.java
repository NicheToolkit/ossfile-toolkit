//package io.github.nichetoolkit.ossfile.controller;
//
//import io.github.nichetoolkit.ossfile.OssfileBulkModel;
//import io.github.nichetoolkit.ossfile.OssfileConstants;
//import io.github.nichetoolkit.ossfile.OssfileRequest;
//import io.github.nichetoolkit.rest.RestException;
//import io.github.nichetoolkit.rest.RestResult;
//import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
//import io.github.nichetoolkit.rest.util.FileUtils;
//import org.springframework.http.ResponseEntity;
//import org.springframework.lang.NonNull;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.nio.channels.FileChannel;
//
//public class PartController {
//
//    @PostMapping("/index/upload")
//    public ResponseEntity<OssfileBulkModel> indexUpload(@NonNull @RequestBody OssfileBulkModel fileIndex) throws RestException {
//        String originalFilename = fileIndex.getName();
//        log.info("the index file will be started uploading at 'indexUpload', filename: {}", originalFilename);
//        OssfileBulkModel createIndex = FileServiceHelper.createFileIndex(fileIndex);
//        OssfileBulkModel fileUpload = fileService.indexUpload(createIndex);
//        return ResponseEntity.ok(fileUpload);
//    }
//
//    @PostMapping("/chunk/upload")
//    public ResponseEntity chunkUpload(@NonNull @RequestPart("file") MultipartFile file,
//                                      @RequestHeader(value = OssfileConstants.CONTENT_RANGE_HEADER) String contentRange,
//                                      OssfileRequest fileRequest) throws RestException {
//        String originalFilename = file.getOriginalFilename();
//        log.info("the chunk file will be started uploading at 'chunkUpload', filename: {}", originalFilename);
//        OssfileBulkModel fileIndex = fileService.chunkUpload(file, contentRange, fileRequest);
//        return ResponseEntity.ok(fileIndex);
//    }
//
//
//    @PostMapping("/slice")
//    public ResponseEntity slice(@NonNull @RequestPart("file") MultipartFile file) throws RestException {
//        String originalFilename = file.getOriginalFilename();
//        assert originalFilename != null;
//        log.info("the file will be started slicing with filename: {}", originalFilename);
//        String filename = FileUtils.filename(originalFilename);
//        String tempPath = commonProperties.getTempPath();
//        try {
//            String cachePath = FileUtils.createPath(tempPath, filename);
//            File originalFile = FileUtils.createFile(cachePath.concat(originalFilename));
//            file.transferTo(originalFile);
//            long chunkSize = 200 * 1024 * 1024L;
//            long sliceSize = originalFile.length() / chunkSize;
//            if (file.getSize() % chunkSize != 0) {
//                sliceSize += 1;
//            }
//            RandomAccessFile accessFile = new RandomAccessFile(originalFile, "rwd");
//            FileChannel accessFileChannel = accessFile.getChannel();
//            for (int i = 1; i <= sliceSize; i++) {
//                long position = (i - 1) * chunkSize;
//                long positionEnd = position + chunkSize;
//                if (i == sliceSize) {
//                    positionEnd = file.getSize();
//                }
//                chunkSize = positionEnd - position;
//                String sliceFilename = cachePath.concat(File.separator).concat(filename).concat("_").concat(String.valueOf(i))
//                        .concat("_").concat(String.valueOf(position))
//                        .concat("_").concat(String.valueOf(positionEnd))
//                        .concat("_").concat(String.valueOf(chunkSize))
//                        .concat("_").concat(String.valueOf(file.getSize()));
//                File sliceFile = FileUtils.createFile(sliceFilename);
//                RandomAccessFile sliceAccessFile = new RandomAccessFile(sliceFile, "rwd");
//                FileChannel sliceAccessFileChannel = sliceAccessFile.getChannel();
//                long transferTo = accessFileChannel.transferTo(position, chunkSize, sliceAccessFileChannel);
//                sliceAccessFileChannel.close();
//            }
//            accessFileChannel.close();
//        } catch (IOException exception) {
//            throw new ServiceErrorException(exception.getMessage());
//        }
//        return RestResult.ok();
//    }
//}
