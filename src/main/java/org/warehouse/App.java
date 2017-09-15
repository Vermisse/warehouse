package org.warehouse;

import javax.sql.*;

import org.apache.commons.dbcp.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.*;
import org.mybatis.spring.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.embedded.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.*;
import org.springframework.core.io.support.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.transaction.*;
import org.springframework.web.servlet.config.annotation.*;
import org.warehouse.filter.*;

@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@Configuration
@MapperScan("org.warehouse.web.dao.*")
public class App extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer {

	@Autowired
	private Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * 设置服务器端口
	 */
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(80);
	}
	
	/**
	 * 数据源
	 */
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.primary")
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		ds.setUrl(env.getProperty("spring.datasource.url"));
		ds.setUsername(env.getProperty("spring.datasource.username"));
		ds.setPassword(env.getProperty("spring.datasource.password"));
		return ds;
	}

	/**
	 * 事务
	 */
	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	/**
	 * 自动实现Dao
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:org/warehouse/web/dao/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	/**
	 * 过滤器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SecurityFilter()).addPathPatterns("/**");
	}
}