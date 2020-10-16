package jp.smartcompany.admin.appmanager.handler;

import cn.hutool.db.handler.BeanListHandler;
import cn.hutool.db.handler.RsHandler;

import jp.smartcompany.admin.appmanager.dto.MastTemplateDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MastTemplateHandler implements RsHandler<List<MastTemplateDTO>> {

    @Override
    public List<MastTemplateDTO> handle(ResultSet resultSet) throws SQLException {
        BeanListHandler<MastTemplateDTO> handler = new BeanListHandler<>(MastTemplateDTO.class);
        return handler.handle(resultSet);
    }

}
