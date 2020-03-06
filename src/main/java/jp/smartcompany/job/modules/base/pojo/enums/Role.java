package jp.smartcompany.job.modules.base.pojo.enums;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
public enum Role {

  /**
   * 超级管理员
   */
  ADMIN(1, "admin"),
  /**
   * 一般用户
   */
  NORMAL(2,"normal"),
  /**
   * 部门领导
   */
  DEPT_LEADER(3, "dept_leader"),
  /**
   * 总务
   */
  GENERAL_AFFAIR(4,"general_affair"),
  /**
   * 一般管理员
   */
  MANAGER(5,"manager");

  @JsonValue
  private final long value;
  private final String desc;

  Role(final int value, final String desc) {
    this.value = value;
    this.desc = desc;
  }

  public long value() {
    return value;
  }

  public String desc() {
    return desc;
  }

  public static Role getByValue(long value) {
    Map<String, Role> roles = EnumUtil.getEnumMap(Role.class);
    Role role = null;
    for (Map.Entry<String, Role> entry : roles.entrySet()) {
      Role type = entry.getValue();
      if (type.value() == value) {
        role = type;
        break;
      }
    }
    return role;
  }

}
