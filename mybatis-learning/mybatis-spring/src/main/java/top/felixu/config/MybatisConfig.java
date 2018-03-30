package top.felixu.config;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.felixu.interceptor.MySpringInterceptorOne;
import top.felixu.interceptor.MySpringInterceptorTwo;
import top.felixu.typehandler.MySpringTypeHandler;
import top.felixu.typehandler.TestSpringTypeHandler;

import javax.sql.DataSource;

/**
 * @author felixu
 * @datetime 2018/3/30 10:29
 */
@Configuration
@MapperScan(basePackages = "top.felixu.dao")
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfig {

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sessionFactory() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{new MySpringTypeHandler(), new TestSpringTypeHandler()});
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new MySpringInterceptorOne(), new MySpringInterceptorTwo()});
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "transactionManagement")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
