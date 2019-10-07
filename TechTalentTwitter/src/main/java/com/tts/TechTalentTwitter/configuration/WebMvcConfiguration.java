/**
 * 
 */
package com.tts.TechTalentTwitter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sloanehouston
 *
 */

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = 
            new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}