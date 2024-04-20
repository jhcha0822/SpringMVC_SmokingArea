package com.sds.smoking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value="mainController")
public class MainController {
		// <mvc:annotation-driven/> 등록 이후부터 SpringMVC에서 컨트롤러 영역의 많은 기능을 지원
		// @RequestMapping(value="", RequestMethod.GET)도 줄여쓰기 가능
		@GetMapping("/smoking")
		public String getMain() {
			return "view/index";
		}
		
}
	