package org.infosys.idp.web.uiconfig;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//@PropertySource("file:${IDPPropertiesPath}/Services/config.properties")
@ComponentScan
public class UiWebConfig extends WebMvcConfigurerAdapter {

	
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    

//     @Override
//     public void addViewControllers(final ViewControllerRegistry registry) {
//         super.addViewControllers(registry);
//         //        if(!UIProperty.getGeFlagForJava()){
// //        registry.addViewController("/").setViewName("forward:/index");}
// //        else{
// //        	registry.addViewController("/").setViewName("forward:/previousConfiguration");
// //        }
//          // registry.addViewController("/index").setViewName("index");
// //        registry.addViewController("/app").setViewName("app");
// //        registry.addViewController("/previousConfiguration").setViewName("previousConfiguration");
//        // registry.addViewController("/{spring:\\w+}").setViewName("forward:/");
//        // registry.addViewController("/**/{spring:\\w+}").setViewName("forward:/");
//        // registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");
// 		registry.addViewController("/login").setViewName("forward:/");
// 		registry.addViewController("/manageEnvironment").setViewName("forward:/");
//         registry.addViewController("/notificationPage").setViewName("forward:/"); 
// 		registry.addViewController("/createConfig").setViewName("forward:/");
// 		registry.addViewController("/createConfig/**").setViewName("forward:/");
// 		registry.addViewController("/previousConfig").setViewName("forward:/");
// 		registry.addViewController("/keycloak").setViewName("forward:/");
// 		registry.addViewController("/previousConfig/**").setViewName("forward:/");
// 		registry.addViewController("/success/").setViewName("forward:/");
// 		registry.addViewController("/pipelines/").setViewName("forward:/");
// 		registry.addViewController("/pipelines/**").setViewName("forward:/");
// 		registry.addViewController("/releaseConfig").setViewName("forward:/");
// 		registry.addViewController("/releaseConfig/**").setViewName("forward:/");
// 		registry.addViewController("/dashboard/**").setViewName("forward:/");
// 		registry.addViewController("/applications/**").setViewName("forward:/");
// 		registry.addViewController("/showConfigurations/**").setViewName("forward:/");
// 		registry.addViewController("/aboutView/**").setViewName("forward:/");
// 		registry.addViewController("/dashboardView/**").setViewName("forward:/");
// 		registry.addViewController("/editApp/**").setViewName("forward:/");
        
//     }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/{spring:\\w+}").setViewName("forward:/");
        registry.addViewController("/**/{spring:\\w+}").setViewName("forward:/");

    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
       
    }

}