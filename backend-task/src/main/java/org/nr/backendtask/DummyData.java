package org.nr.backendtask;


import org.nr.backendtask.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DummyData implements CommandLineRunner {


    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Override
    public void run(String... args) throws Exception {


    }
}
