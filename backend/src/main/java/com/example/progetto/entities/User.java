package com.example.progetto.entities;


import com.example.progetto.support.View;
import com.example.progetto.utility.ORDER_STATUS;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.sql.Timestamp;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "utenti")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente", nullable = false)
    @JsonView(View.Summary.class)
    private Long userID;

    @Basic
    @Column(name= "nome", nullable = false)
    @JsonView(View.Summary.class)
    private String firstName;

    @Basic
    @Column(name = "cognome", nullable = false)
    @JsonView(View.Summary.class)
    private String lastName;

    @Basic
    @Column(name="email", nullable = false)
    @JsonView(View.Summary.class)
    private String email;

    @Basic
    @Column(name = "indirizzo", nullable = false)
    @JsonView(View.Summary.class)
    private String address;

    @Basic
    @Column(name ="ruolo", nullable = false)
    @JsonView(View.Summary.class)
    private String role;

    @Basic
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_registrazione", nullable = false)
    @JsonView(View.Summary.class)
    private Timestamp registrationDate;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.Detail.class)
    private List<Order> orders;

    @OneToOne(mappedBy = "userID", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.Detail.class)
    private Cart cart;

    @JsonView(View.Detail.class)
    public Order getOrdineAttivo() {
        if (orders != null) {
            for (Order order : orders) {
                if (order.getOrderStatus() == ORDER_STATUS.ACTIVE) {
                    return order;
                }
            }
        }
        return null;
    }

    public User(){
        this.cart = new Cart(this);
    }
}