package jp.smartcompany.job.modules.base.pojo.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public abstract class AbstractDBBean {

    @Version
    private Long versionno;
}
