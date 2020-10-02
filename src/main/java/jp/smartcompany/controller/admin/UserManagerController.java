package jp.smartcompany.controller.admin;

import jp.smartcompany.admin.usermanager.logic.UserManagerMainLogic;
import jp.smartcompany.boot.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ユーザ管理
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/usermanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerController {

    private final UserManagerMainLogic userManagerMainLogic;

    // http://localhost:6879/sys/usermanager/search?psSite=Admin&page=2
    @GetMapping("search")
    public PageUtil search(@RequestParam Map<String,Object> params) {
        return userManagerMainLogic.search(params);
    }

}
