package com.jwst.common.util;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to create a pooling data source object using commons DBCP
 *
 */
public class DBPool {

    private static Logger log = LoggerFactory.getLogger(DBPool.class);


    private static PoolingDataSource dsPool;


    /**
     * fetches the data source 
     *
     * @return data source pool
     */

    public static org.apache.commons.dbcp.PoolingDataSource getDataSource() {
        if (dsPool == null) {

            dsPool = registerDataSource();
        }
        return dsPool;

    }

    /**
     * register the data source 
     *
     * @return pooling database object
     */

    private static PoolingDataSource registerDataSource() {
        // create a database connection
        String user = AppConfig.getProperty("datasource.username");
        String password = AppConfig.getProperty("datasource.password");
        String connectionURI = AppConfig.getProperty("datasource.url");
        String driverClassName = AppConfig.getProperty("datasource.driverClassName");
        String validationQuery = "select 1";
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException ex) {
            log.error(ex.toString(), ex);
        }
        GenericObjectPool connectionPool = new GenericObjectPool(null);
        connectionPool.setMaxActive(25);
        connectionPool.setTestOnBorrow(true);
        connectionPool.setMinIdle(2);
        connectionPool.setMaxWait(15000);
        connectionPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectionURI, user, password);

        new PoolableConnectionFactory(connectionFactory, connectionPool, null, validationQuery, false, true);

        return new PoolingDataSource(connectionPool);
    }
    
}

