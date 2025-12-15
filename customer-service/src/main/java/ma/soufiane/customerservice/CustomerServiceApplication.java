package ma.soufiane.customerservice;

import ma.soufiane.customerservice.entity.Customer;
import ma.soufiane.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            for(int i=1;i<=10;i++){
                customerRepository.save(Customer.builder().name("Customer "+i).email("customer"+i+"@email.com").build());
            }
        };
    }
}
