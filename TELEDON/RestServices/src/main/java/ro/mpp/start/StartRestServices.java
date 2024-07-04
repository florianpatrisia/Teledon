package ro.mpp.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

@ComponentScan("ro.mpp")
@SpringBootApplication
public class StartRestServices {

    public static void main(String[] args) {
        SpringApplication.run(StartRestServices.class, args);
    }

    @Bean(name="props")
    @Primary
    public Properties getBdProperties(){
        Properties props = new Properties();
        try {
            Resource resource = new ClassPathResource("/db.config");
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            props.load(new ByteArrayInputStream(bytes));
            System.out.println("Baza de date gasita!!");
        } catch (IOException e) {
            System.err.println("Configuration file bd.config not found" + e);
        }
        return props;
    }
}
