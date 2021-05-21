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

    public static <E> Result<E> build() {
        return new Result<>();
    }

    private static <E> Result<E> build(String code, String info) {
        return Result.build(code, info, null);
    }

    private static <E> Result<E> build(String code, String info, E data, String... holders) {
        if (null != holders && holders.length != 0) {
            int i = 0;
            StringBuffer buffer = new StringBuffer();
            Matcher matcher = Pattern.compile("\\{\\{\\w*}}").matcher(info);
            while (i < holders.length && matcher.find()) {
                matcher.appendReplacement(buffer, holders[i++]);
            }
            info = matcher.appendTail(buffer).toString();
        }
        Result<E> result = build();
        return result.setCode(code)
                .setInfo(info)
                .setData(data);
    }

    private static <E> Result<E> build(ResultEnum resultEnum, String... holders) {
        return build(resultEnum, null, holders);
    }

    private static <E> Result<E> build(ResultEnum resultEnum, E data, String... holders) {
        return build(resultEnum.getCode(), resultEnum.getInfo(), data, holders);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static <E> Result<E> fail(String info) {
        return build(ResultEnum.FAIL.getCode(), info);
    }

    public static <E> Result<E> fail(Result<?> result) {
        return build(result.getCode(), result.getInfo());
    }

    public static <E> Result<E> fail(ResultEnum resultEnum) {
        return build(resultEnum);
    }

    /**
     * 填充占位符
     */
    public static <E> Result<E> fail(ResultEnum resultEnum, String... holders) {
        return build(resultEnum, holders);
    }

    public static <E> Result<E> success() {
        return build(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return build(ResultEnum.SUCCESS, data);
    }

    /**
     * 渲染转换TableVO
     */
    public static <T> Result<TableVO<T>> table(Result<List<T>> Result) {
        return Result.isFail() ?
                build(Result.getCode(), Result.getInfo()) :
                success(TableVO.build(Result.getData()));
    }

    /**
     * 返回表格渲染模型
     */
    public static <T> Result<TableVO<T>> table(List<T> list) {
        return Result.success(TableVO.build(list));
    }


}
