package com.example.common.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.common.exception.BusinessException;
import com.example.common.exception.basic.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

@Component
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    @Resource
    private RestTemplate restTemplate;

    private static HttpUtil httpUtil;

    @PostConstruct
    public void init(){
        httpUtil = this;
        httpUtil.restTemplate = this.restTemplate;
    }

    /**
     * 发送请求Get请求
     *
     * @param url   请求接口
     * @param param 请求参数
     * @param paramType 请求参数类型 json：false  String：true
     */
    public JSONObject sendGet(String url, String param, boolean paramType) throws Exception {
        JSONObject jsonObject = null;
        try {
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<String> resEntity = null;
            if (!paramType) {
                HttpEntity httpEntity = new HttpEntity<>(param, headers);
                resEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            }else {
                HttpEntity httpEntity = new HttpEntity<>(headers);
                resEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, param);
            }

            jsonObject = JSONUtil.parseObj(resEntity.getBody());
        } catch (Exception e) {
            if (e.toString().contains("timed out")) {
                throw new BusinessException(ResponseCode.ECSC_CONNECTION_TIMEOUT);
            }
            logger.error("接口调用失败{}",e);
            throw new BusinessException(ResponseCode.ECSC_GETDATA_FAIL);
        }
        return jsonObject;
    }

    /**
     * 发送请求Get请求
     *
     * @param url   请求接口
     * @param params 请求参数
     */
    public JSONObject sendGet(String url, Map<Object, Object> params){
        StringBuffer stringBuffer = new StringBuffer(url);

        if (params instanceof Map) {
            Iterator iterator = ((Map) params).entrySet().iterator();
            if (iterator.hasNext()) {
                stringBuffer.append("?");
                Object element;
                while (iterator.hasNext()) {
                    element = iterator.next();
                    Map.Entry<String, Object> entry = (Map.Entry) element;
                    if (entry.getValue() != null) {
                        stringBuffer.append(element).append("&");
                    }
                    url = stringBuffer.substring(0, stringBuffer.length() - 1);
                }
            }
        } else {
            throw new BusinessException(ResponseCode.ECSC_REQUEST_PARAM_ERROR);
        }

        JSONObject jsonObject = null;
        try {
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<String> resEntity = null;
            HttpEntity httpEntity = new HttpEntity<>(headers);
            resEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            jsonObject = JSONUtil.parseObj(resEntity.getBody());
        } catch (Exception e) {
            if (e.toString().contains("timed out")) {
                throw new BusinessException(ResponseCode.ECSC_CONNECTION_TIMEOUT);
            }
            logger.error("接口调用失败{}",e);
            throw new BusinessException(ResponseCode.ECSC_GETDATA_FAIL);
        }
        return jsonObject;
    }

    /**
     * 封装的get请求
     *
     * @param url
     * @param params
     * @return
     */
//    public static JSONObject sendGet(String url, Map<Object, Object> params) {
//
//        StringBuffer stringBuffer = new StringBuffer(url);
//
//        if (params instanceof Map) {
//            Iterator iterator = ((Map) params).entrySet().iterator();
//            if (iterator.hasNext()) {
//                stringBuffer.append("?");
//                Object element;
//                while (iterator.hasNext()) {
//                    element = iterator.next();
//                    Map.Entry<String, Object> entry = (Map.Entry) element;
//                    if (entry.getValue() != null) {
//                        stringBuffer.append(element).append("&");
//                    }
//                    url = stringBuffer.substring(0, stringBuffer.length() - 1);
//                }
//            }
//        } else {
//            throw new BaseException(String.valueOf(ResponseCode.ECSC_REQUEST_PARAM_ERROR));
//        }
//
//        String response = new RestTemplate().getForObject(url, String.class);
//        JSONObject jsonObject = JSONUtil.parseObj(response);
//        return jsonObject;
//    }

    /**
     * 发送请求Put请求
     *
     * @param url   请求接口
     * @param param 请求参数
     */
    public JSONObject sendPut(String url, JSONObject param) {
        JSONObject jsonObject = null;
        try {
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity<>(param, headers);
            ResponseEntity<String> resEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
            jsonObject = JSONUtil.parseObj(resEntity.getBody());
        } catch (Exception e) {
            if (e.toString().contains("timed out")) {
                throw new BusinessException(String.valueOf(ResponseCode.ECSC_CONNECTION_TIMEOUT));
            }
            logger.error("接口调用失败{}",e);
            throw new BusinessException(String.valueOf(ResponseCode.ECSC_GETDATA_FAIL));
        }
        return jsonObject;
    }

    /**
     * 发送请求Post请求
     *
     * @param url   请求接口
     * @param param 请求参数
     */
    public JSONObject sendPost(String url, JSONObject param) {
        JSONObject jsonObject = null;
        try {
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity<>(param, headers);
            jsonObject = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        } catch (Exception e) {
            if (e.toString().contains("timed out")) {
                throw new BusinessException(String.valueOf(ResponseCode.ECSC_CONNECTION_TIMEOUT));
            }
            logger.error("接口调用失败{}",e);
            throw new BusinessException(String.valueOf(ResponseCode.ECSC_GETDATA_FAIL));
        }
        return jsonObject;
    }
}