package jp.smartcompany.boot.configuration.security.access;

import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public interface HasUrlPermission {

    boolean hasPermission(HttpServletRequest request, Authentication authentication) throws SQLException;

    List<MastSystemDO> getSystemList();

    List<MenuGroupBO> loadSystemMenu(HttpSession httpSession);

}
