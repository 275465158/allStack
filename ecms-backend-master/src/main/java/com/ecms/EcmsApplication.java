package com.ecms;

import com.ecms.listener.PurchaseOrderReviewEndEventListener;
import com.ecms.web.filter.LoginFilter;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.Filter;

@SpringBootApplication(exclude={
		org.activiti.spring.boot.SecurityAutoConfiguration.class,
		SecurityAutoConfiguration.class
})
@EnableTransactionManagement

@MapperScan("com.ecms.dal.mapper")
public class EcmsApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EcmsApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true).allowedHeaders("*").allowedOrigins("*").allowedMethods("*");
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(loginFilter());
		registration.addUrlPatterns("/*");
		registration.setName("loginFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean(name = "loginFilter")
	public Filter loginFilter() {
		return new LoginFilter();
	}


}
