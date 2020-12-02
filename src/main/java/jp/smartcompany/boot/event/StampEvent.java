package jp.smartcompany.boot.event;

import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@ToString
public class StampEvent extends ApplicationEvent {

    private String action;

    public StampEvent(LoginAccountBO loginAccountBO) {
        super(loginAccountBO);
    }

    public StampEvent(LoginAccountBO loginAccountBO, String action) {
        super(loginAccountBO);
        this.action = action;
    }

}
