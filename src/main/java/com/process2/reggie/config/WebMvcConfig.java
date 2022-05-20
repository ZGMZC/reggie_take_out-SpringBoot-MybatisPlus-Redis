package com.process2.reggie.config;

import com.process2.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j  //日志
/*@Configuration的作用：标注在类上，配置spring容器(应用上下文)。相当于把该类作为spring的xml配置文件中的<beans>。
@Configuration注解的类中，使用@Bean注解标注的方法，返回的类型都会直接注册为bean。*/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/static/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/static/front/");
    }

    /**
     * 扩展MVC框架的消息转换器
     *    所谓消息转换器，通俗来说将晦涩的消息转换成通俗易懂的消息。
     * 　　对于java来说，通俗易懂的消息肯定是对象了。
     * 　　对于请求和响应都有对应的body，这个body就是我们关注的消息。
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter=new MappingJackson2HttpMessageConverter();
        //设置对象转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
    }
}
