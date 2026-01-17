package com.cloudnative.k8s.userservice.repository;

import com.cloudnative.k8s.userservice.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public class UserRepository {

    private static List<User> users = List.of(
            new User(1L, "Alice Smith", "alicesmith@gmail.com"),
            new User(2L, "Bob Johnson", "bobjohnson@gmail.com"),
            new User(3L, "Charlie Brown", "charliebrown@gmail.com"),
            new User(4L, "David Wilson", "davidwilson@gmail.com"),
            new User(5L, "Emma Davis", "emmadavis@gmail.com"),
            new User(6L, "Frank Miller", "frankmiller@gmail.com"),
            new User(7L, "Grace Taylor", "gracetaylor@gmail.com"),
            new User(8L, "Henry Anderson", "henryanderson@gmail.com"),
            new User(9L, "Isabella Thomas", "isabellathomas@gmail.com"),
            new User(10L, "Jack Moore", "jackmoore@gmail.com")
    );

    public List<User> findAll(){
        return users.stream().toList();
    }

}
