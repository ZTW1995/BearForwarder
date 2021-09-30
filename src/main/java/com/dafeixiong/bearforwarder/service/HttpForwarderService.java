package com.dafeixiong.bearforwarder.service;

import com.dafeixiong.bearframework.util.net.Result;

/***
 *
 * @author ZTW
 * @date 2021-09-29 17:46
 *
 */
public interface HttpForwarderService {

    /**
     * 转发http请求
     * @param url 待转发的目标Url
     * @return 转发后的请求响应结果字符串
     */
    String forwardHttp(String url);
}
