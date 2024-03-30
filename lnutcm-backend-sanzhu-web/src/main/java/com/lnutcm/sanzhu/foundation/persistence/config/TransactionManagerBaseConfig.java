package com.lnutcm.sanzhu.foundation.persistence.config;

import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
public class TransactionManagerBaseConfig implements TransactionManagerCustomizer<DataSourceTransactionManager> {
    /**
     * Customize the given transaction manager.
     *
     * @param transactionManager the transaction manager to customize<br>
     *                           <p></p>
     * 1.Oracle 数据库本身也是支持只读事务的，也就是只读模式，
     *                           但是文档很明确的说明了只读模式只能通过oracle数据库本身进行设置：<br>
     *<p></p>
     * Read-only connections are supported by the Oracle server, but not by the Oracle JDBC drivers<.
     *<p></p>
     * For transactions, the Oracle server supports only the TRANSACTION_READ_COMMITTED and
     *                           TRANSACTION_SERIALIZABLE transaction isolation levels.<br>>
     *                           The default is TRANSACTION_READ_COMMITTED.<br>
     *                           Use the following methods of the oracle.jdbc.OracleConnection
     *                           interface to get and set the level:<br>
     *<p></p>
     * getTransactionIsolation: Gets this connection's current transaction isolation level.<br>
     *<p></p>
     * setTransactionIsolation: Changes the transaction isolation level, using one of the TRANSACTION_* values.
     *
     *
     *2.Spring 开启开关
     *Spring 4.3.7 包含 SPR-15210，它首次提供了真正的只读数据库事务。必须使用 setEnforceReadOnly 显式启用该功能
     * DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
     * txManager.setEnforceReadOnly(true);<br>
     * 这将导致
     * SET TRANSACTION READ ONLY
     * 正在执行。<br>
     * 这应该适用于 Oracle、MySQL 和 Postgres。借助 Oracle，
     * 该功能将为您提供读取一致性，包括可重复读取。如果数据库具有不同的语法，
     * 则可以子类化并重写 prepareTransactionalConnection。DataSourceTransactionManager
     *
     * 请注意，早期版本的 Spring 只会调用 Connection.setReadOnly（true），
     * 它为您提供只读连接而不是只读事务（它使用旧的 Oracle 驱动程序，但这是一个错误）。
     * 这也是 的默认行为。DataSourceTransactionManager
     * https://marschall.github.io/2017/02/13/spring-read-only.html
     */
    @Override
    public void customize(DataSourceTransactionManager transactionManager) {
        transactionManager.setEnforceReadOnly(true); // 让事务管理器进行只读事务层面上的优化  建议开启


    }



}
