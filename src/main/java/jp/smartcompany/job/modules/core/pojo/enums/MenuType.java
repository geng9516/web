package jp.smartcompany.job.modules.core.pojo.enums;

import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Getter
public enum MenuType {

  /**
   * カタログ
   */
  CATALOG(0,"カタログ"),
  /**
   * メニュー
   */
  MENU(1,"メニュー"),
  /**
   * ボタン
   */
  BUTTON(2,"ボタン");

  @EnumValue
  @JsonValue
  private final Integer value;
  private final String desc;

  MenuType(final Integer value, final String desc) {
    this.value = value;
    this.desc = desc;
  }

  public static MenuType getByValue(Integer value) {
    Map<String, MenuType> map = EnumUtil.getEnumMap(MenuType.class);
    MenuType menuType = null;
    for (Map.Entry<String, MenuType> entry : map.entrySet()) {
      MenuType gender = entry.getValue();
      if (gender.getValue().equals(value)) {
        menuType = gender;
        break;
      }
    }
    return menuType;
  }

}