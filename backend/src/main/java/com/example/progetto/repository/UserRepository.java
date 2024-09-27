package com.example.progetto.repository;

import com.example.progetto.entities.Order;
import com.example.progetto.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {



    boolean existsByEmail(String email);
    List<User> findAllByFirstNameAndLastName(String nome, String cognome);

    List<User> findByFirstName(String nome);
    List<User> findByLastName(String cognome);

    List<User> findByEmailContaining(String param);
    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    Page<User> findByEmailContaining(Pageable pageable, String param);
}
