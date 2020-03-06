package jp.smartcompany.job.modules.core.pojo.enums;

import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Map;

/**
 * 性别
 * @author Xiao Wenpeng
 */
@Getter
public enum Gender {

    /**
     * 不明
     */
    UNKNOWN(0,"不明"),
    /**
     * 男
     */
    MALE(1,"男"),
    /**
     *　女
     */
    FEMALE(2,"女");

    @EnumValue
    @JsonValue
    private final Integer value;
    private final String desc;

    Gender(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static Gender getByValue(Integer value) {
        Map<String, Gender> genders = EnumUtil.getEnumMap(Gender.class);
        Gender g = null;
        for (Map.Entry<String, Gender> entry : genders.entrySet()) {
            Gender gender = entry.getValue();
            if (gender.getValue().equals(value)) {
                g = gender;
                break;
            }
        }
        return g;
    }

}
