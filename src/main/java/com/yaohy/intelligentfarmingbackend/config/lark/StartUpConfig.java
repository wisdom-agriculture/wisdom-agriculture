package com.yaohy.intelligentfarmingbackend.config.lark;

import com.yaohy.intelligentfarmingbackend.config.lark.LarkRobot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpConfig implements CommandLineRunner {

    @Autowired
    private LarkRobot larkRobot;

    @Override
    public void run(String... args) throws Exception {
        larkRobot.finish();
    }

}
