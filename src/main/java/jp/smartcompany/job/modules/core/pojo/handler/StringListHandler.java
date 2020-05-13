package jp.smartcompany.job.modules.core.pojo.handler;

import cn.hutool.db.handler.HandleHelper;
import cn.hutool.db.handler.RsHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
public class StringListHandler implements RsHandler<List<String>> {
    private static final long serialVersionUID = -2846240126316979895L;
    private boolean caseInsensitive;

    public static cn.hutool.db.handler.EntityListHandler create() {
        return new cn.hutool.db.handler.EntityListHandler();
    }

    public StringListHandler() {
        this(false);
    }

    public StringListHandler(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public List<String> handle(ResultSet rs) throws SQLException {
        return HandleHelper.handleRs(rs, new ArrayList(), this.caseInsensitive);
    }

}
