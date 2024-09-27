package com.example.progetto.controller;

import com.example.progetto.dto.OrderDTO;
import com.example.progetto.dto.UserDTO;
import com.example.progetto.entities.Order;
import com.example.progetto.entities.User;
import com.example.progetto.services.KeycloakUserServiceImpl;
import com.example.progetto.services.UserService;
import com.example.progetto.dto.UserRegistrationRecord;
import com.example.progetto.support.View;
import com.example.progetto.support.exception.EmailAlreadySubscribedException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utenti")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService u){this.userService=u;}

    @Autowired
    private KeycloakUserServiceImpl keycloakUserService;

    /*GETTER*/

    @GetMapping("/")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Page<UserDTO>> getUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userService.findAll(pageable);
        Page<UserDTO> userDTOPage = usersPage.map(user -> {
            UserDTO dto = new UserDTO();
            dto.setUserID(user.getUserID());
            dto.setEmail(user.getEmail());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            return dto;
        });
        return ResponseEntity.ok(userDTOPage);
    }
    @GetMapping("/find")
    @PreAuthorize("hasRole('admin')")
    @JsonView(View.Summary.class)
    public ResponseEntity<Page<User>> findByEmail(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam String param){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findByEmailContaining(pageable, param));
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/get")
    @JsonView(View.Summary.class)
    public User getById(@RequestParam Long id){
        return userService.findByID(id);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getByEmail/{email}")
    public User getByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }

    @GetMapping("getByEmailContaining/{email}")
    @PreAuthorize("hasRole('admin')")
    public List<UserDTO> getByEmailContaining(@PathVariable String email){
        List<User> users = userService.emailContaining(email);

        // Mappa la lista degli utenti a una lista di DTO
        List<UserDTO> listDTO = users.stream()
                .map(user -> new UserDTO(user.getUserID(), user.getFirstName(), user.getLastName(), user.getEmail()))
                .collect(Collectors.toList());

        // Restituisce la lista di DTO
        return listDTO;
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/firstNameAndLastName")
    public List<User> getAllByNomeAndCognome(@RequestParam String nome, @RequestParam String cognome){
        return userService.findAllByFirstNameAndLastName(nome,cognome);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/firstName")
    public List<User> getAllByNome(@RequestParam String nome){
        return userService.findAllByName(nome);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/lastName")
    public List<User> getAllByCognome(@RequestParam String cognome){
        return  userService.findAllByLastName(cognome);
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getIdByEmail")
    public String getKeycloakUserIdByEmail(@RequestParam String email){
        return keycloakUserService.getUserIdByEmail(email);
    }


    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String email){
        String id = keycloakUserService.getUserIdByEmail(email);
        if(id!=null){
            keycloakUserService.deleteUserById(id);
            userService.deleteUser(userService.findByEmail(email).getUserID());
            return ResponseEntity.ok("Utente eliminato con successo");
        }else{
            return new ResponseEntity<>("Non esiste alcun utente associato alla mail: "+email+".\nOperazione non effettuata.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok("Utente eliminato con successo");
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>("Non esiste alcun utente associato all'id: "+id+".\nOperazione non effettuata.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity createUser(@RequestBody UserRegistrationRecord userRegistrationRecord)  {
        keycloakUserService.createUser(userRegistrationRecord);
        User u = new User();
        u.setFirstName(userRegistrationRecord.firstName());
        u.setLastName(userRegistrationRecord.lastName());
        u.setEmail(userRegistrationRecord.email());
        u.setRole("customer");
        u.setAddress(userRegistrationRecord.address());
        u.setOrders(new LinkedList<Order>());
        try{
            userService.registerUser(u);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (EmailAlreadySubscribedException e){
            return new ResponseEntity<>("Esiste già un account associato a questa e-mail", HttpStatus.BAD_REQUEST);
        }
    }
}

