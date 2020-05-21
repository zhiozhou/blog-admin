package priv.zhou.common.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhou
 */
@Getter
@Setter
public class Page {

    private Integer page = 1;

    private Integer limit = 10;

    private boolean count = true;

    public Page() {
    }

    public Page(boolean count) {
        this.count = count;
    }

    public Page(Integer limit) {
        this.limit = limit;
        if (0 == limit) {
            this.count = false;
        }
    }
}
