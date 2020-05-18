package jp.smartcompany.job.modules.core.pojo.handler;

import cn.hutool.db.handler.HandleHelper;
import cn.hutool.db.handler.RsHandler;
import jp.smartcompany.job.modules.core.pojo.bo.OrganisationBO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author X02217
 */
public class OrganisationEntityListHandler implements RsHandler <ArrayList<OrganisationBO>>{

    private static final long serialVersionUID = 0;
    private boolean caseInsensitive;

    public static cn.hutool.db.handler.EntityListHandler create() {
        return new cn.hutool.db.handler.EntityListHandler();
    }

    public OrganisationEntityListHandler() {
        this(false);
    }

    public OrganisationEntityListHandler(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public ArrayList<OrganisationBO> handle(ResultSet rs) throws SQLException {
        return (ArrayList) HandleHelper.handleRs(rs, new ArrayList(), this.caseInsensitive);
    }
}
