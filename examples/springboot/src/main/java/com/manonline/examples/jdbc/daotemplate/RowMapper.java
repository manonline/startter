package com.manonline.examples.jdbc.daotemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by davidqi on 2/12/17.
 */
public interface RowMapper {
    Object mapRow(ResultSet rs) throws SQLException;
}
