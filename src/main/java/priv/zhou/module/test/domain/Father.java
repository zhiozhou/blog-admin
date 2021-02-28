package priv.zhou.module.test.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Father {

    private String name = "father";

    public void print(){
        System.out.println(name);
    }

}
