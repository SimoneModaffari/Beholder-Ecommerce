package com.example.progetto.services;

import com.example.progetto.entities.Cart;
import com.example.progetto.entities.User;
import com.example.progetto.repository.UserRepository;
import com.example.progetto.support.exception.EmailAlreadySubscribedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User registerUser(User u) throws EmailAlreadySubscribedException{
        if(userRepository.existsByEmail(u.getEmail())){
            throw new EmailAlreadySubscribedException();
        }
        return userRepository.save(u);
    }

    @Transactional
    public void deleteUser(Long id) throws IllegalArgumentException{
        User u = findByID(id);
        if( u==null || !userRepository.existsByEmail(u.getEmail())){
            throw new IllegalArgumentException();
        }
        userRepository.delete(u);
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<User> findByEmailContaining(Pageable pageable, String param) {
        return userRepository.findByEmailContaining(pageable, param);
    }

    @Transactional(readOnly = true)
    public User findByID(Long id){
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()){
            return optUser.get();
        }
        else{
                System.out.println("L'utente non esiste");
                return null;
        }
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public List<User> emailContaining(String param){
        return userRepository.findByEmailContaining(param);
    }
    public List<User> findAllByFirstNameAndLastName(String nome, String cognome){
        return userRepository.findAllByFirstNameAndLastName(nome,cognome);
    }

    public List<User> findAllByName(String nome){
        return userRepository.findByFirstName(nome);
    }

    public List<User> findAllByLastName(String cognome){
        return userRepository.findByLastName(cognome);
    }

    public Cart getCartByEmail(String email){
        User u = userRepository.findByEmail(email);
        return u.getCart();
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
