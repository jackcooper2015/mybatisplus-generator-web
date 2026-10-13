package com.reapal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@SpringBootApplication
@Slf4j
public class CodeGeneratorApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(CodeGeneratorApplication.class, args);
        var environment = context.getEnvironment();
        log.info("Generator started at http://{}:{}", environment.getProperty("server.address", "127.0.0.1"),
                environment.getProperty("local.server.port", environment.getProperty("server.port", "8088")));
    }



	@RequestMapping("/")
	public String home(){
		return "redirect:/index";
	}

}
