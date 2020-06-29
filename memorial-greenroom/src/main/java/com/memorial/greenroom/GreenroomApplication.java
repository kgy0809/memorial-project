package com.memorial.greenroom;

import com.memorial.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/28 17:49
 */
@SpringBootApplication
@MapperScan("com.memorial.mapper")
public class GreenroomApplication {
    public static void main(String[] args) {
        SpringApplication.run(GreenroomApplication.class, args);
    }
    //定义IdWorker的Bean
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }



}
