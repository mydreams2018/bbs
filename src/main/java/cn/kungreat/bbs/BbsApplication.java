package cn.kungreat.bbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.kungreat.bbs.mapper")
public class BbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbsApplication.class, args);
	}

/*	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createConnector());
		return tomcat;
	}
	@Bean
	public Connector createConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		try {
			connector.setScheme("http");
			connector.setSecure(false);
			connector.setPort(80);
			connector.setRedirectPort(443);
			return connector;
		}
		catch (Exception ex) {
			throw new IllegalStateException("can't access keystore: [" + "keystore"
					+ "] or truststore: [" + "keystore" + "]", ex);
		}
	}*/
}