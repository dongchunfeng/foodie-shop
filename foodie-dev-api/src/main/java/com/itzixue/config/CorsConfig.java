package com.itzixue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Description 跨域配置
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/3
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {

    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        //添加跨域地址
        config.addAllowedOrigin("http://106.13.196.47:8080");
        config.addAllowedOrigin("http://106.13.196.47");

        //设置是否发送cookie信息
        config.setAllowCredentials(true);
        //允许的长度
        config.addAllowedHeader("*");
        //请求的方法
        config.addAllowedMethod("*");

        //为url映射路劲
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(corsSource);
    }

}
