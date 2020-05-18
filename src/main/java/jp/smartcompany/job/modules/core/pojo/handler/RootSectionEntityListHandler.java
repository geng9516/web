package jp.smartcompany.job.modules.core.pojo.handler;

import cn.hutool.db.handler.HandleHelper;
import cn.hutool.db.handler.RsHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author X02217
 */
public class RootSectionEntityListHandler implements RsHandler <List<String>>{

    private static final long serialVersionUID = 0;
    private boolean caseInsensitive;

    public static cn.hutool.db.handler.EntityListHandler create() {
        return new cn.hutool.db.handler.EntityListHandler();
    }

    public RootSectionEntityListHandler() {
        this(false);
    }

    public RootSectionEntityListHandler(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public List<String> handle(ResultSet rs) throws SQLException {
        return (List) HandleHelper.handleRs(rs, new ArrayList(), this.caseInsensitive);
    }
}
