package com.yi.xyesjy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@MapperScan("com.yi.xyesjy.mapper")
public class XyesjyApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyesjyApplication.class, args);
    }
}
