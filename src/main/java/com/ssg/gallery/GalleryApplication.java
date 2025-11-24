package com.ssg.gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GalleryApplication {

	public static void main(String[] args) {
		// app 자체를 서버에 주입하여 실행
		SpringApplication.run(GalleryApplication.class, args);
	}

}
