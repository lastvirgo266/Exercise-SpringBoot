package board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.ApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:/application.properties")
//@EnableTransactionManagement //어노테이션 기반 트랜잭션 활성화
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
//	@Bean
//	public PlatformTransactionManager transactionManager() throws Exception
//	{
//		return new DataSourceTransactionManager(dataSource());
//	}
	
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariconfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariconfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/sql-*.xml"));
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	
	@Bean
	@ConfigurationProperties(prefix="spring.jpa")
	public Properties hibernateConfig()	{
		return new Properties();
	}
	
	
	
	

	

}
