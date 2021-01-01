package priv.zhou.common.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.OutEnum;

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


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    public static <E> Result<E> build(OutEnum outEnum) {
        return build(outEnum, null);
    }

    public static <E> Result<E> build(OutEnum outEnum, E data) {
        return build(outEnum.getCode(), outEnum.getInfo(), data);
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

    /**
     * 状态玛是否错误
     */
    @JsonIgnore
    public boolean isFail() {
        return !OutEnum.SUCCESS.getCode().equals(this.code);
    }

    /**
     * 返回api错误
     */
    public static <E> Result<E> fail(OutEnum outEnum) {
        return build(outEnum.getCode(), outEnum.getInfo());
    }

    /**
     * 返回api错误
     */
    public static <E> Result<E> fail(OutEnum outEnum, String info) {
        return build(outEnum.getCode(), info);
    }


    /**
     * 返回api成功
     */
    public static Result<NULL> success() {
        return build(OutEnum.SUCCESS);
    }


    /**
     * 返回api成功
     */
    public static <T> Result<T> success(T data) {
        return build(OutEnum.SUCCESS, data);
    }


    /**
     * OutVO转OutVO
     */
    public static <T> Result<T> ofBO(Result<T> Result) {
        return build(Result.getCode(), Result.getInfo(), Result.getData());
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
