package jp.smartcompany.admin.usermanager.logic.impl;

import jp.smartcompany.admin.usermanager.dto.UserManagerCondDTO;
import jp.smartcompany.admin.usermanager.logic.UserManagerMainLogic;
import jp.smartcompany.boot.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userManagerMainLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerMainLogicImpl implements UserManagerMainLogic {

    /**
     * 法人リスト取得
     * @param conditions 検索条件
     * @return PageUtil 検索結果
     */
     public PageUtil search(UserManagerCondDTO conditions) {
       return null;
     }

}
