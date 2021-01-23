package priv.zhou.common.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.misc.ResultEnum;

import java.util.List;

/**
 * 全局返回
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
        return !ResultEnum.SUCCESS.equals(this);
    }


    public static <E> Result<E> build() {
        return new Result<>();
    }

    public static <E> Result<E> build(ResultEnum resultEnum) {
        return build(resultEnum, null);
    }

    public static <E> Result<E> build(ResultEnum resultEnum, E data) {
        return build(resultEnum.getCode(), resultEnum.getInfo(), data);
    }

    public static <E> Result<E> build(String code, String info) {
        return Result.build(code, info, null);
    }

    public static <E> Result<E> build(String code, String info, E data) {
        Result<E> result = new Result<>();
        return result.setCode(code)
                .setInfo(info)
                .setData(data);


    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * 返回api错误
     */
    public static <E> Result<E> fail(Result<?> res) {
        return build(res.getCode(), res.getInfo());
    }

    /**
     * 返回api错误
     */
    public static <E> Result<E> fail(ResultEnum resultEnum) {
        return build(resultEnum.getCode(), resultEnum.getInfo());
    }

    /**
     * 返回api错误
     */
    public static <E> Result<E> fail(ResultEnum resultEnum, String info) {
        return build(resultEnum.getCode(), info);
    }


    /**
     * 返回api成功
     */
    public static <E> Result<E> success() {
        return build(ResultEnum.SUCCESS);
    }


    /**
     * 返回api成功
     */
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


}
