package com.example.graphqltutorial.entities;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="todo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID todo_id;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="account_id")
    private AccountEntity account;

    public UUID getAccountId() {
        return account.getAccount_id();
    }

    public TodoEntity(String description, AccountEntity accountEntity) {
        this.description = description;
        this.account = accountEntity;
    }
}
