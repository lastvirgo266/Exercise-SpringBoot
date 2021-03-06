package board.configuration;

import java.nio.charset.Charset;

import javax.servlet.Filter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	
	//인터셉터 빈
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoggerInterceptor());
	}

	/* 아래는 한글처리를 위한 인코딩 과정으로 스프링 부트 2.1.x 버전에는 이미 인코딩 필터가 적용되어있다
	 * 따라서 2.1.x 하위버전을 쓰거나 다른 인코딩 필터를 추가해야하는 경우에만 사용한다  */

//	@Bean
//	public Filter characterEncodingFilter()
//	{
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//		
//		return characterEncodingFilter;
//	}
//	
//	@Bean
//	public HttpMessageConverter<String> responseBody()
//	{
//		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//	}
	
	
	
	@Bean
	public CommonsMultipartResolver multipartResolver()
	{
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		
		commonsMultipartResolver.setDefaultEncoding("UTF-8"); //파일의 인코딩을 UTF-8로 설정
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); // 업로드 되는 파일의 크기를 제한 (단위 : byte)
		return commonsMultipartResolver;
	}
}