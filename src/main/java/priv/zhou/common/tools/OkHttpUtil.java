package priv.zhou.common.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import priv.zhou.common.domain.vo.OutVO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@SuppressWarnings("all")
public class OkHttpUtil {

    private final static OkHttpClient httpClient = AppContextUtil.getBean(OkHttpClient.class);

    private final static Class<OutVO> DEFAULT_VO = OutVO.class;

    public static OutVO httpGet(String desc, String url) {
        return httpGet(desc, url, Maps.newHashMap());
    }

    public static OutVO httpGet(String desc, String url, Map<String, String> params) {
        return httpGet(desc, url, params, OutVO.class);
    }

    public static <T> T httpGet(String desc, String url, Class<T> clazz) {
        return httpGet(desc, url, null, clazz);
    }

    public static <T> T httpGet(String desc, String url, Map<String, String> params, Class<T> clazz) {
        if (nonNull(params) && !params.isEmpty()) {
            url += "?" + Joiner.on("&").withKeyValueSeparator("=").join(params);
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        log.info("请求 {} 接口, url -->{}", desc, url);
        return call(desc, request, clazz);
    }

    public static OutVO httpPost(String desc, String url) {
        return httpPost(desc, url, Maps.newHashMap());
    }

    public static OutVO httpPost(String desc, String url, Map<String, Object> params) {
        return httpPost(desc, url, params, OutVO.class);
    }

    /**
     * query post
     * application/x-www-form-urlencoded;charset=UTF-8
     */
    public static <T> T httpPost(String desc, String url, Map<String, Object> params, Class<T> clazz) {
        FormBody.Builder builder = new FormBody.Builder();
        if (nonNull(params) && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof List) {
                    putList(builder, entry);
                } else {
                    builder.add(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        log.info("请求 {} 接口, url -->{} | 请求报文 -->{}", desc, url, JSON.toJSONString(params));
        return call(desc, request, clazz);
    }


    public static OutVO httpPost(String desc, String url, JSONObject json) {
        return httpPost(desc, url, json, DEFAULT_VO);
    }

    /**
     * json post
     */
    public static <T> T httpPost(String desc, String url, JSONObject json, Class<T> clazz) {
        String jsonStr = json.toJSONString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
        log.info("请求 {} 接口, url -->{} | 请求报文 -->{}", desc, url, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return call(desc, request, clazz);
    }

    /**
     * 核心调用
     */
    @SuppressWarnings("unchecked")
    private static <T> T call(String desc, Request request, Class<T> clazz) {
        Stopwatch stopWatch = Stopwatch.createStarted();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && nonNull(response.body())) {
                String result = response.body().string();
                log.info("收到 {} 接口响应, 响应报文 -->{}, 耗时 {}ms", desc, result, stopWatch.elapsed(TimeUnit.MILLISECONDS));
                return clazz.equals(String.class) ? (T) result : ParseUtil.object(result, clazz);
            }
        } catch (Exception e) {
            log.info("收到 " + desc + " 接口响应异常 e -->", e);
        }
        return null;
    }

    /**
     * query 请求中放入集合
     */
    @SuppressWarnings("all")
    private static void putList(FormBody.Builder builder, Map.Entry<String, Object> entry) {
        if (isNull(entry.getValue())) {
            return;
        }
        String key = entry.getKey() + "[%s]";
        List list = (List) entry.getValue();
        for (int i = 0; i < list.size(); i++) {
            builder.add(String.format(key, i), String.valueOf(list.get(i)));
        }
    }

//    /**
//     * Post请求发送xml数据
//     */
//    public static String httpPost(String url, String xml) {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//        return call(request);
//    }
}
