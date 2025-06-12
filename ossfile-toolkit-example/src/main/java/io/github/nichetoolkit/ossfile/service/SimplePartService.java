package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.mapper.OssfilePartMapper;
import io.github.nichetoolkit.rice.stereotype.RestService;

@RestService(mapperType= OssfilePartMapper.class)
public class SimplePartService extends DefaultPartService{
}
