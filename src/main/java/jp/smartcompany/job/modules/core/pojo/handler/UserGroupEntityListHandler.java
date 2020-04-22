package jp.smartcompany.job.modules.core.pojo.handler;

import cn.hutool.db.handler.HandleHelper;
import cn.hutool.db.handler.RsHandler;
import jp.smartcompany.job.modules.core.pojo.bo.PQueryUserGroupBO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
public class UserGroupEntityListHandler implements RsHandler<List<PQueryUserGroupBO>> {
    private static final long serialVersionUID = -2846240126316979895L;
    private boolean caseInsensitive;

    public static cn.hutool.db.handler.EntityListHandler create() {
        return new cn.hutool.db.handler.EntityListHandler();
    }

    public UserGroupEntityListHandler() {
        this(false);
    }

    public UserGroupEntityListHandler(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public List<PQueryUserGroupBO> handle(ResultSet rs) throws SQLException {
        return (List) HandleHelper.handleRs(rs, new ArrayList(), this.caseInsensitive);
    }

}
