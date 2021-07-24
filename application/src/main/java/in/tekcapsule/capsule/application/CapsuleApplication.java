package in.tekcapsule.capsule.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"in.tekcapsule.capsule","in.tekcapsule.core"})
public class CapsuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapsuleApplication.class, args);
    }
}
