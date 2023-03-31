package com.rk.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rk.blog.entities.Role;
import com.rk.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {
	@Autowired
	private RoleRepo repo;

	@Bean
	public BCryptPasswordEncoder bcryEncoder() {
		return new BCryptPasswordEncoder();

	}

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Role r1 = new Role();
		r1.setId(1);
		r1.setName("ADMIN");

		Role r2 = new Role();
		r2.setId(2);
		r2.setName("USER");

		List<Role> list = List.of(r1, r2);
		List<Role> all = repo.saveAll(list);

		

	}

}
