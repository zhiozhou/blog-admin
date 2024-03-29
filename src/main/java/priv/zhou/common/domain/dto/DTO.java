package priv.zhou.common.domain.dto;

import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;


/**
 * 废弃等待完全删除
 * @deprecated
 */
@NoArgsConstructor
public abstract class DTO<PO> {


    /**
     * PO转换构造器
     * 当属性名与类型相同时会自动赋值
     * 其他情况可在子级构造器中实现
     */
    public DTO(PO po) {
        if (nonNull(po)) {
            BeanUtils.copyProperties(po, this);
        }
    }


    /**
     * DTO转换为PO
     * 当属性名与类型相同时会自动赋值
     * 其他情况可在重写方法
     */
    public PO toPO() {
        PO po = newPO();
        BeanUtils.copyProperties(this, po);
        return po;
    }

    /**
     * DTO转PO判空
     * 为空返回null
     */
    protected <DT extends DTO<P>, P> P toPO(DT dto) {
        return null == dto ? null : dto.toPO();
    }

    /**
     * 创建定义的PO对象
     * 获取当前类指定的第一个泛型<PO>并实例化
     */
    @SuppressWarnings("unchecked")
    private PO newPO() {
        try {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<PO> clazz = (Class<PO>) superClass.getActualTypeArguments()[0];
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("实例化PO对象出错", e);
        }
    }


    /**
     * po转dto
     */
    public static <DT extends DTO<P>, P extends Serializable> DT ofPO(P po, Function<P, DT> constructor) {
        return null == po ? null : constructor.apply(po);
    }


    /**
     * po集合转换为dto集合
     */
    public static <DT extends DTO<P>, P> List<DT> ofPO(List<P> poList, Function<P, DT> constructor) {
        List<DT> DTList = Lists.newArrayList();
        if (null == poList || poList.isEmpty()) {
            return DTList;
        }
        return poList.stream().map(constructor).collect(Collectors.toList());
    }

//    public void setId(Integer id)  {
//        this.id = RsaUtil.encode(String.valueOf(id), RSA_PUBLIC_KEY);
//    }

}




