package com.example.progetto.support.exception;

public class EmailAlreadySubscribedException extends Throwable {

    public EmailAlreadySubscribedException(){
        System.out.println("Esiste già un account associato a questa e-mail!");
    }
}
