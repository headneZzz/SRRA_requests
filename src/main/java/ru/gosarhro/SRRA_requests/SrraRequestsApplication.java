package ru.gosarhro.SRRA_requests;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
public class SrraRequestsApplication {
    public static void main(String[] args) {
        setDefaultLocale();
        SpringApplication.run(SrraRequestsApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    private static void setDefaultLocale() {
        Locale.setDefault(Locale.Category.DISPLAY, Locale.ENGLISH);
        Locale.setDefault(Locale.ENGLISH);
    }
}
