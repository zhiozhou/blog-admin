package priv.zhou.common.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.enums.ResultEnum;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局Rest返回结果
 *
 * @author zhou
 * @since 0.1.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class Result<T> {

    /**
     * 系统状态
     */
    private String code;

    /**
     * 系统返回信息
     */
    private String info;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 状态玛是否错误
     */
    @JsonIgnore
    public boolean isFail() {
        return !ResultEnum.SUCCESS.getCode().equals(this.code);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Result<T> render(String... values) {
        if (null != values && values.length != 0) {
            int i = 0;
            StringBuffer buffer = new StringBuffer();
            Matcher matcher = Pattern.compile("\\{\\{\\w*}}").matcher(info);
            while (i < values.length && matcher.find()) {
                matcher.appendReplacement(buffer, values[i++]);
            }
            info = matcher.appendTail(buffer).toString();
        }
        return this;
    }

    public static <E> Result<E> build() {
        return new Result<>();
    }

    private static <E> Result<E> build(String code, String info, E data, String... values) {
        Result<E> result = build();
        return result.setCode(code)
                .setInfo(info)
                .setData(data)
                .render(values);
    }

    private static <E> Result<E> build(ResultEnum resultEnum, E data, String... values) {
        return build(resultEnum.getCode(), resultEnum.getInfo(), data, values);
    }


    public static <E> Result<E> fail(String info) {
        return build(ResultEnum.FAIL.getCode(), info, null);
    }

    public static <E> Result<E> fail(ResultEnum resultEnum) {
        return build(resultEnum, null);
    }

    public static <E> Result<E> fail(ResultEnum resultEnum, E data) {
        return build(resultEnum, data);
    }

    public static <E> Result<E> fail(Result<?> result) {
        return build(result.getCode(), result.getInfo(), null);
    }

    public static <E> Result<E> fail(Result<?> result, E data) {
        return build(result.getCode(), result.getInfo(), data);
    }

    /**
     * 填充占位符
     */
    public static <E> Result<E> render(ResultEnum resultEnum, String... values) {
        return build(resultEnum, null, values);
    }

    public static <E> Result<E> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return build(ResultEnum.SUCCESS, data);
    }

    /**
     * 渲染转换TableVO
     */
    public static <T> Result<TableVO<T>> table(Result<List<T>> result) {
        return result.isFail() ? fail(result) :
                success(TableVO.build(result.getData()));
    }

    /**
     * 返回表格渲染模型
     */
    public static <T> Result<TableVO<T>> table(List<T> list) {
        return Result.success(TableVO.build(list));
    }


}
