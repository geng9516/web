package jp.smartcompany.job.modules.quartz.pojo.enums;

import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Getter
public enum JobState {
    /**
     * 正常
     */
    NORMAL(1),
    /**
     * 暂停
     */
    PAUSE(0);

    @EnumValue
    @JsonValue
    private final int value;

    JobState(final int value) {
        this.value = value;
    }

    public static JobState getByValue(int value) {
        Map<String, JobState> states = EnumUtil.getEnumMap(JobState.class);
        JobState jobState = null;
        for (Map.Entry<String, JobState> entry : states.entrySet()) {
            JobState type = entry.getValue();
            if (type.getValue() == value) {
                jobState = type;
                break;
            }
        }
        return jobState;
    }
}
