package com.wuxp.env.database;

import org.springframework.core.io.AbstractResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@link DatabasePropertySourceLoader}
 * @author wuxp
 */
public class DataSourceResource extends AbstractResource {


    private final DataSource dataSource;

    public DataSourceResource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getDescription() {
        return "DataSource resource";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
