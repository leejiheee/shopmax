package com.shopmax.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// WebMvcConfigurer: 파일을 읽어올 경로 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	String uploadPath = "file:///C:/shop/"; //업로드할 경로
	
	//웹 브라우저에서 URL이 images로 시작하는 경우 uploadPath 에 설정한 폴더를 기준으로 파일을 읽어본다.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
				.addResourceLocations(uploadPath);
	}
}