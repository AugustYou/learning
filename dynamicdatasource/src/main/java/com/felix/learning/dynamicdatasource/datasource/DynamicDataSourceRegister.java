package com.felix.learning.dynamicdatasource.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.felix.learning.dynamicdatasource.constants.DsConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author : felix.
 * @createTime : 2017/4/10.
 * @desc : 动态数据源注册，在启动类中加上@Import(DynamicDataSourceRegister.class).
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    private ConversionService conversionService = new DefaultConversionService();
    private PropertyValues dataSourcePropertyValues;

    // 数据源
    private DataSource defaultDataSource;
    private DruidDataSource dataSource = new DruidDataSource();
    private Map<String, DataSource> customDataSources = new HashMap<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("dataSource", defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        // 添加更多数据源
        targetDataSources.putAll(customDataSources);
        customDataSources.keySet()
                .forEach(key -> DynamicDataSourceContextHolder.dataSourceIds.add(key));

        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);

        logger.debug("Dynamic DataSource Registry");
    }

    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            DruidDataSource druidDataSource = (DruidDataSource) dataSource.clone();

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            druidDataSource.setDriverClassName(driverClassName);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);

            return druidDataSource;
        } catch (CloneNotSupportedException e) {
            logger.error("build druidDataSource failed", e);
        }
        return null;
    }

    // 加载多数据源配置
    @Override
    public void setEnvironment(Environment env) {
        initDataSource(env);
        initDefaultDataSource(env);
        initCustomDataSources(env);
    }

    private void initDataSource(Environment env) {
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, DsConfigConstants.DRUID_PREFIX);
        logger.debug("--------------log-----------------");
        dataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
        dataSource.setInitialSize(propertyResolver.getProperty("initialSize", Integer.TYPE));
        dataSource.setMinIdle(propertyResolver.getProperty("minIdle", Integer.TYPE));
        dataSource.setMaxActive(propertyResolver.getProperty("maxActive", Integer.TYPE));
        dataSource.setMinEvictableIdleTimeMillis(propertyResolver.getProperty("minEvictableIdleTimeMillis", Long.TYPE));
        dataSource.setTestOnBorrow(propertyResolver.getProperty("testOnBorrow", Boolean.TYPE));
        dataSource.setTestWhileIdle(propertyResolver.getProperty("testWhileIdle", Boolean.TYPE));
        dataSource.setRemoveAbandoned(propertyResolver.getProperty("removeAbandoned", Boolean.TYPE));
        dataSource.setRemoveAbandonedTimeout(propertyResolver.getProperty("removeAbandonedTimeout", Integer.TYPE));
        dataSource.setLogAbandoned(propertyResolver.getProperty("logAbandoned", Boolean.TYPE));
        dataSource.setPoolPreparedStatements(propertyResolver.getProperty("poolPreparedStatements", Boolean.TYPE));
        dataSource.setMaxOpenPreparedStatements(propertyResolver.getProperty("maxOpenPreparedStatements", Integer.TYPE));
        logger.debug("-----------------------------");
    }

    // 初始化主数据源
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        Map<String, Object> dsMap = new RelaxedPropertyResolver(env, DsConfigConstants.DATASOURCE_PREFIX).getSubProperties(".");
        defaultDataSource = buildDataSource(dsMap);
        dataBinder(defaultDataSource, env);
    }

    // 为DataSource绑定更多数据
    private void dataBinder(DataSource dataSource, Environment env){
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        dataBinder.setConversionService(conversionService);
        dataBinder.setIgnoreNestedProperties(false);
        dataBinder.setIgnoreInvalidFields(false);
        dataBinder.setIgnoreUnknownFields(true);
        Optional.ofNullable(dataSourcePropertyValues).ifPresent(dataSourcePropertyValue -> {
            Map<String, Object> rpr = new RelaxedPropertyResolver(env, DsConfigConstants.DATASOURCE_PREFIX).getSubProperties(".");
            Map<String, Object> values = new HashMap<>(rpr);
            // 排除已经设置的属性
            values.remove("driver-class-name");
            values.remove("url");
            values.remove("username");
            values.remove("password");
            dataSourcePropertyValues = new MutablePropertyValues(values);
        });
        dataBinder.bind(dataSourcePropertyValues);
    }

    // 初始化更多数据源
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, DsConfigConstants.MULTIPLE_DATASOURCE_PREFIX);
        String dsPrefixs = propertyResolver.getProperty("names");
        Arrays.stream(dsPrefixs.split(","))
                .forEach(dsPrefix -> {
                    Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
                    DataSource ds = buildDataSource(dsMap);
                    customDataSources.put(dsPrefix, ds);
                    dataBinder(ds, env);
                });
    }
}
