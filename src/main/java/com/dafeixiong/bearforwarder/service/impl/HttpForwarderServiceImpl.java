package com.dafeixiong.bearforwarder.service.impl;

import com.dafeixiong.bearforwarder.service.HttpForwarderService;
import com.dafeixiong.bearframework.core.annotation.Service;
import com.dafeixiong.bearframework.util.net.HttpUtil;

/***
 *
 * @author ZTW
 * @date 2021-09-29 17:47
 *
 */
@Service(name = "HttpForwarderService")
public class HttpForwarderServiceImpl implements HttpForwarderService {

    @Override
    public String forwardHttp(String url) {
        String result;
        try {
            result = HttpUtil.sendGet(url);
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
}
