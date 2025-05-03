package com.lance017;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动程序
 *
 * @author writer
 */
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableAsync
public class WriterApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(WriterApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  系统启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
