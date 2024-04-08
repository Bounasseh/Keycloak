package com.example.ecomapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
}

interface ProductRepository extends JpaRepository<Product, Long> {
}

@Controller
class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/index")
//    @ResponseBody
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        System.out.println(productRepository.findAll());
        return "index";
    }
}

@SpringBootApplication
public class EcomAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomAppApplication.class, args);
    }

    @Autowired
    ProductRepository productRepository;

    @Bean
    public void saveProducts() {
        productRepository.save(new Product(null, "PC", 5000));
        productRepository.save(new Product(null, "Phone", 2000));
        productRepository.save(new Product(null, "Tablet", 1000));

        productRepository.findAll().forEach(product -> {
            System.out.println(product.getName());
        });
    }

}
