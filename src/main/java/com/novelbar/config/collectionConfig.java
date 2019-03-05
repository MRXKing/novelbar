package com.novelbar.config;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import com.novelbar.model.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import com.novelbar.model.util;

@SpringBootConfiguration
public class collectionConfig {


    @Bean
    public hpBookModel hpBookModel() {
        return new hpBookModel();
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*"); // 允许任何的head头部
        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
        corsConfiguration.addAllowedMethod("*"); // 允许任何的请求方法
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    @Bean
    public util getUtil() {
        return new util();
    }

    @Bean
    public registerModel registerModel() {
        return new registerModel();
    }

    @Bean
    public loginModel loginModel(){ return  new loginModel(); }

    @Bean
    public getUserInfoModel getUserInfoModel(){ return new getUserInfoModel(); }

    @Bean
    public getBookInfoModel getBookInfoModel(){ return new getBookInfoModel(); }
}
