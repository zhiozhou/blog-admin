package priv.zhou.module.test.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Son extends  Father {

    public Son() {
        super.setName("son");
    }

    public static void main(String[] args) {
        Son son = new Son();
        son.print();
    }
}
