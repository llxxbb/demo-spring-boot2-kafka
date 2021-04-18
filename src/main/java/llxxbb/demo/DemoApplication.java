package llxxbb.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        try {
            // the following property used by logback
            System.setProperty("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ignore) {
        }
        SpringApplication.run(DemoApplication.class, args);
    }

}
