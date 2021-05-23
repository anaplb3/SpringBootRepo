package com.example.roteiro1;

import com.example.roteiro1.filter.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Roteiro1Application {

	@Bean
	public FilterRegistrationBean<Filter> filterJwt() {
		FilterRegistrationBean<Filter> filtroJwt = new FilterRegistrationBean<Filter>();
		filtroJwt.setFilter(new Filter());
		filtroJwt.addUrlPatterns("/perguntas/*");
		return filtroJwt;
	}

	public static void main(String[] args) {
		SpringApplication.run(Roteiro1Application.class, args);
	}

}
