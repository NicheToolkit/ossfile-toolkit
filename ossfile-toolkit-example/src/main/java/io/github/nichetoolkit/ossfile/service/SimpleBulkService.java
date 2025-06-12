package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.mapper.OssfileBulkMapper;
import io.github.nichetoolkit.rice.stereotype.RestService;

@RestService(mapperType= OssfileBulkMapper.class)
public class SimpleBulkService extends DefaultBulkService{
}
