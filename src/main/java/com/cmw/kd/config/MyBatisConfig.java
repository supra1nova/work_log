package com.cmw.kd.config;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.cmw.kd", sqlSessionFactoryRef = "sqlSessionFactory")
@RequiredArgsConstructor
public class MyBatisConfig {
  private static final String CONFIG_LOCATION_PATH = "classpath:mybatis/mybatis-config.xml";
  private static final String MAPPERS_LOCATION_PATH = "classpath:mybatis/mappers/**/*.xml";
  private final ResourceLoader resourceLoader;
  private final ResourcePatternResolver resourcePatternResolver;

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource(CONFIG_LOCATION_PATH));
    sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(MAPPERS_LOCATION_PATH));
    return sqlSessionFactoryBean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
