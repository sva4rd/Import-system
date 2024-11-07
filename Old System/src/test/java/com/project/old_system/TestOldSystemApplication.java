package com.project.old_system;

import org.springframework.boot.SpringApplication;

public class TestOldSystemApplication {

    public static void main(String[] args) {
        SpringApplication.from(OldSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
