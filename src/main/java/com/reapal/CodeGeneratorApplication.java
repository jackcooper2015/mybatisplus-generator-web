package com.reapal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
@ServletComponentScan
@SpringBootApplication
public class CodeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeGeneratorApplication.class, args);
	}


	@RequestMapping("/")
	public String home(){
		return "redirect:/index";
	}

}
