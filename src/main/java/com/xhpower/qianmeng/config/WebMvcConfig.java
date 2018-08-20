package com.xhpower.qianmeng.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xhpower.qianmeng.filter.FrontSessionFilter;
import com.xhpower.qianmeng.filter.SessionFilter;

/**
 * 配置servlet 、filter、listener
 */
@Configuration
public class WebMvcConfig {

	/**
	 * 配置过滤器--后台
	 * 
	 */
	@Bean
	public FilterRegistrationBean<Filter> someFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(sessionFilter());
		registration.addUrlPatterns("/page/*", "/admin/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("sessionFilter");
		return registration;
	}

	/**
	 * 创建一个bean
	 * 
	 * @return
	 */
	@Bean(name = "sessionFilter")
	public Filter sessionFilter() {
		return new SessionFilter();
	}
	
	/**
	 * 配置过滤器--前台
	 * 
	 */
	@Bean
	public FilterRegistrationBean<Filter> frontFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(frontSessionFilter());
		registration.addUrlPatterns("/*");
		registration.setName("frontSessionFilter");
		return registration;
	}

	/**
	 * 创建一个bean
	 * 
	 * @return
	 */
	@Bean(name = "frontSessionFilter")
	public Filter frontSessionFilter() {
		return new FrontSessionFilter();
	}

	/**
	 * 解决ie数据保存出现下载对话框的问题
	 * 
	 * @return
	 */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1.需要定义一个convert转换消息的对象;
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		// 2处理ie浏览器保存数据时出现下载json数据问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.TEXT_PLAIN);
		fastMediaTypes.add(MediaType.APPLICATION_JSON);
		// 3.在convert中添加配置信息.
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
		fastJsonConfig.setSerializerFeatures(
				// 避免循环引用
				SerializerFeature.DisableCircularReferenceDetect,
				// 是否输出值为null的字段
				SerializerFeature.WriteMapNullValue,
				// 输出key时是否使用双引号
				SerializerFeature.QuoteFieldNames,
				// 数值字段如果为null,输出为0,而非null
				SerializerFeature.WriteNullNumberAsZero,
				// List字段如果为null,输出为[],而非null
				SerializerFeature.WriteNullListAsEmpty,
				// 字符类型字段如果为null,输出为"",而非null
				SerializerFeature.WriteNullStringAsEmpty,
				// Boolean字段如果为null,输出为false,而非null
				SerializerFeature.WriteNullBooleanAsFalse,
				// Date的日期转换器 (默认格式: YYYY-MM-dd HH:mm:ss)
				SerializerFeature.WriteDateUseDateFormat);
		// fastJsonConfig.setDateFormat("YYYY-MM-dd HH:mm:ss");
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
		return new HttpMessageConverters(converter);
	}
}
