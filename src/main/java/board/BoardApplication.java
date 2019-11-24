package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableJpaAuditing
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class}, basePackages = {"board"}) //베이스패키지로 지정된 패키지하위에서 JPA엔티티검색 --> 따라서 여기에 
//스프링 자동구성 제외설정
@SpringBootApplication(exclude= {MultipartAutoConfiguration.class})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
	

	

}
