package com.reapal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@EnableWebMvc
@ServletComponentScan
@SpringBootApplication
@Slf4j
public class CodeGeneratorApplication {

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(CodeGeneratorApplication.class, args);
		log.info("\n======================================================\n\t"+
					"Application is running !!! \n\t"+
					"Auth: \tjackcooper\n\t"+
					"Email: \t1040078821@qq.com\n\t"+
					"Access URLs:\t http://{}:8088\n"+
					"======================================================",
				InetAddress.getLocalHost().getHostAddress()
		);
	}


	@RequestMapping("/")
	public String home(){
		return "redirect:/index";
	}

}
