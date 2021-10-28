package com.dafeixiong.bearforwarder.service.impl;

import com.dafeixiong.bearforwarder.service.HttpForwarderService;
import com.dafeixiong.bearframework.core.annotation.Service;
import com.dafeixiong.bearframework.util.StringUtil;
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
    public String forwardHttp(String url, String encode) {
        String result;
        if (StringUtil.isEmpty(encode)) {
            encode = "UTF-8";
        }
        try {
            result = HttpUtil.sendGet(url, encode);
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
}
