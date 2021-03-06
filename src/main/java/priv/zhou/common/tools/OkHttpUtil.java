package priv.zhou.common.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.framework.exception.RestException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SuppressWarnings("all")
/**
 * Okhttp 工具类
 * @author zhou
 * @since 2020.5.15
 */
public class OkHttpUtil {
    private final static Class<Result> DEFAULT_CLASS = Result.class;

    //    private final static OkHttpClient httpClient = null;
    private final static OkHttpClient httpClient = SpringUtils.getBean(OkHttpClient.class);

    public static <E> Result<E> httpGet(String desc, String url) {
        return httpGet(desc, url, Maps.newHashMap());
    }

    public static <E> Result<E> httpGet(String desc, String url, Map<String, String> params) {
        return httpGet(desc, url, params, Result.class);
    }

    public static <T> T httpGet(String desc, String url, Class<T> clazz) {
        return httpGet(desc, url, null, clazz);
    }

    public static <T> T httpGet(String desc, String url, Map<String, String> params, Class<T> clazz) {
        if (null != params && !params.isEmpty()) {
            url += "?" + Joiner.on("&").withKeyValueSeparator("=").join(params);
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        log.info("请求 {} 接口, url -->{}", desc, url);
        return call(desc, request, clazz);
    }

    public static <E> Result<E> httpPost(String desc, String url) {
        return httpPost(desc, url, Maps.newHashMap());
    }

    public static <E> Result<E> httpPost(String desc, String url, Map<String, Object> params) {
        return httpPost(desc, url, params, Result.class);
    }

    /**
     * query post
     * application/x-www-form-urlencoded;charset=UTF-8
     * 如包含 File || MultipartFile @see httpMultipart()
     */
    public static <T> T httpPost(String desc, String url, Map<String, Object> params, Class<T> clazz) {
        FormBody.Builder builder = new FormBody.Builder();
        if (null != params && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (null == value) {
                } else if (value instanceof List) {
                    List<Object> list = (List<Object>) value;
                    for (int i = 0; i < list.size(); i++) {
                        builder.add(String.format(key + "[%s]", i), String.valueOf(list.get(i)));
                    }
                } else {
                    builder.add(key, String.valueOf(value));
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


    public static <E> Result<E> httpMultipart(String desc, String url, Map<String, Object> params) {
        return httpMultipart(desc, url, params, DEFAULT_CLASS);
    }

    /**
     * multipart post
     */
    public static <T> T httpMultipart(String desc, String url, Map<String, Object> params, Class<T> clazz) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MediaType.parse("multipart/form-data"));
        if (null != params && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (null == value) {
                } else if (value instanceof File) {
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(MimeEnum.getValue(file)), file));
                    params.put(key, file.getName());
                } else if (value instanceof MultipartFile) {
                    try {
                        MultipartFile file = (MultipartFile) value;
                        builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(MimeEnum.getValue(file)), file.getBytes()));
                        params.put(key, file.getOriginalFilename());
                    } catch (IOException e) {
                        throw new RestException(Result.fail(ResultEnum.ERROR_SYSTEM));
                    }
                } else if (value instanceof List) {
                    List<Object> list = (List<Object>) value;
                    for (int i = 0; i < list.size(); i++) {
                        builder.addFormDataPart(String.format(key + "[%s]", i), String.valueOf(list.get(i)));
                    }
                } else {
                    builder.addFormDataPart(key, String.valueOf(value));
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


    /**
     * json post
     */
    public static Result<Object> httpPost(String desc, String url, JSONObject json) {
        return httpPost(desc, url, json, DEFAULT_CLASS);
    }

    /**
     * json post
     * application/json; charset=utf-8
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
            if (response.isSuccessful() && null != response.body()) {
                String result = response.body().string();
                log.info("收到 {} 接口响应, 响应报文 -->{}, 耗时 {}ms", desc, result, stopWatch.elapsed(TimeUnit.MILLISECONDS));
                return clazz.equals(String.class) ? (T) result : ParseUtil.object(result, clazz);
            }
        } catch (SocketTimeoutException e) {
            log.info("断开 {} 接口响应超时, 耗时 {}ms", desc, stopWatch.elapsed(TimeUnit.MILLISECONDS));
            if (clazz.equals(DEFAULT_CLASS)) {
                return (T) Result.fail(ResultEnum.TIMEOUT_RESPONSE);
            }
        } catch (Exception e) {
            log.info("收到 " + desc + " 接口响应异常 e -->", e);
        }
        return null;
    }


    public enum MimeEnum {
        TXT("txt", "text/plain"),
        JSON("json", "application/json"),
        XML("xml", "application/xml"),
        PNG("png", "image/png"),
        JPG("jpg", "image/jpeg"),
        JPEG("jpeg", "image/jpeg"),
        GIF("gif", "image/gif"),
        TIF("tif", "image/tiff"),
        TIFF("tiff", "image/tiff");

        MimeEnum(String ext, String value) {
            this.ext = ext;
            this.value = value;
        }

        private String ext;

        private String value;

        public static MimeEnum get(File file) {
            return get(FileUtil.getExt(file));
        }

        public static MimeEnum get(MultipartFile file) {
            return get(FileUtil.getExt(file));
        }

        public static MimeEnum get(String ext) {
            if (!StringUtils.isEmpty(ext)) {
                for (MimeEnum mime : MimeEnum.values()) {
                    if (mime.ext.equals(ext)) {
                        return mime;
                    }
                }
            }
            return TXT;
        }

        public static String getValue(File file) {
            return get(file).value;
        }

        public static String getValue(MultipartFile file) {
            return get(file).value;
        }

        public static String getValue(String ext) {
            return get(ext).value;
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

