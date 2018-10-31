package com.ssm.promotion.core.controller.base;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/** 
 * @description viewController
 * @author  liu66
 * @date 创建时间：2018-6-4 上午9:17:58 
 * @version 1.0 
 * @since  
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.ssm.promotion.core.controller")
public class BasicViewController extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("/jsonp/index").setViewName("jsonp/index");
	}
	
}
