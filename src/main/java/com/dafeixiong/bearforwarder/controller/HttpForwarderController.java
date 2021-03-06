package com.dafeixiong.bearforwarder.controller;

import com.dafeixiong.bearforwarder.service.HttpForwarderService;
import com.dafeixiong.bearframework.core.annotation.*;
import com.dafeixiong.bearframework.util.constant.AesModelEnum;
import com.dafeixiong.bearframework.util.net.Result;
import com.dafeixiong.bearframework.util.notice.EmailUtil;
import com.dafeixiong.bearframework.util.security.AesUtil;

import javax.ws.rs.HttpMethod;
import java.io.IOException;

/***
 * Http请求转发接口Controller
 * @author ZTW
 * @date 2021-09-29 17:37
 *
 */
@Controller(path = "/forward", isRest = true)
public class HttpForwarderController {

    /**
     * 配置的加密密钥
     */
    @Value("${securityKey}")
    private String securityKey;

    /**
     * 配置用于接收密钥通知邮件的邮箱地址
     */
    @Value("${email}")
    private String email;

    @AutoWire(name = "HttpForwarderService")
    private HttpForwarderService httpForwarderService;

    /**
     * 将当前的加密密钥以邮件形式发到自己的邮箱
     * @return
     */
    @RequestMapping(path = "/getKey", method = HttpMethod.POST)
    public Result<String> getKey() {
        EmailUtil.sendEmailsWithConfig(
                email,
                "密钥通知",
                "当前使用的加密密钥为：[ " + securityKey + " ]",
                null,
                "");
        return Result.valueOfSuccess();
    }

    /**
     *
     * @param securityUrl 加密后的目标url
     * @return 加密后的url响应结果字符串
     */
    @RequestMapping(path = "/get", method = HttpMethod.GET)
    public Result<String> get(@RequestParam(name = "securityUrl") String securityUrl) {
        String targetUrl = AesUtil.decodeUrlSafe(securityUrl, securityKey, AesModelEnum.AES_ECB_PKCS5Padding);
        String content = httpForwarderService.forwardHttp(targetUrl, "");
        content = AesUtil.encode(content, securityKey, AesModelEnum.AES_ECB_PKCS5Padding);
        return Result.valueOfSuccess(content);
    }

    /**
     *
     * @param securityUrl 加密后的目标url
     * @return 加密后的url响应结果字符串
     */
    @RequestMapping(path = "/charsetGet", method = HttpMethod.GET)
    public Result<String> get(@RequestParam(name = "securityUrl") String securityUrl,
                              @RequestParam(name = "charset")String charset) {
        String targetUrl = AesUtil.decodeUrlSafe(securityUrl, securityKey, AesModelEnum.AES_ECB_PKCS5Padding);
        charset = AesUtil.decodeUrlSafe(charset, securityKey, AesModelEnum.AES_ECB_PKCS5Padding);
        String content = httpForwarderService.forwardHttp(targetUrl, charset);
        content = AesUtil.encode(content, securityKey, AesModelEnum.AES_ECB_PKCS5Padding);
        return Result.valueOfSuccess(content);
    }

    /**
     * 调用底层系统API执行 curl 命令
     * @param securityUrl 加密后的目标url
     * @return
     */
    @RequestMapping(path = "/curl", method = HttpMethod.GET)
    public Result<String> curl(@RequestParam(name = "securityUrl") String securityUrl) {
        try {
            String targetUrl = AesUtil.decodeUrlSafe(securityUrl, securityKey, AesModelEnum.AES_ECB_PKCS5Padding);
            Runtime.getRuntime().exec("curl " + targetUrl).waitFor();
        } catch (InterruptedException | IOException e) {
            return Result.valueOfError(e);
        }
        return Result.valueOfSuccess();
    }
}
