package board.configuration;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
/*
application.properties를 사용할 수 있도록 설정 파일의 위치를 지정
@PropertySourc 어노테이션을 추가해서 다른 설정 파일도 사용할 수 있다.
 */
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	/*
	application.properties에 설정했던 데이터베이스 관련 정보를 사용하도록 지정
	@ConfigurationProperties 어노테이션에 prefix가 spring.datasource.hikari로
	설정되어 있기 때문에 spring.datasource.hikari로 시작하는 설정을 이용해서 히카리CP의 설정 파일을 생성
	*/	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	/*
	앞에서 만든 히카리CP의 설정 파일을 이요해서 데이터베이스와연결하는 데이터 소스를 생성
	데이터 소스가 정상적으로 생성되었는지 확인하기 위해서 데이터 소스를 출력 
	*/	
	@Bean
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		/*
		스프링 마이바티스에서는 SqlSessionFacotry를 생성하기 위해서 SqlSessionFactoryBean을 사용
		 */
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		/*
		앞에서 만든 데이터소스 설정
		*/
		sqlSessionFactoryBean.setDataSource(dataSource);
		/*
		마이바티스 매퍼 파일의 위치를 설정
		매퍼는 애플리케이션에서 사용할 SQL을 담고 있는 XML 파일을 의미
		매퍼를 등록할 때에는 매퍼 파일을 하나씩 따로 등록할 수도 있지만 하나의 애플리케이션에는 일반적으로
		많은 수의 매퍼 파일이 존재하고, 이를 하나씩 등록하기 어렵습니다. 그러므로 패턴을 기반으로 한번에 등록하는 것이 좋다.
		*/		
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
