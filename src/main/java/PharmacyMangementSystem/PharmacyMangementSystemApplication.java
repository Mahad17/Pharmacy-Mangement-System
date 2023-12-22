package PharmacyMangementSystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
public class PharmacyMangementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyMangementSystemApplication.class, args);		
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
