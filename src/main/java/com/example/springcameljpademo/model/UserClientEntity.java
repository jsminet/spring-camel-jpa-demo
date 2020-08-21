package com.example.springcameljpademo.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MD3431 on 03/08/2020
 * books entity
 *      user_client_id serial NOT NULL,
 *      user_id integer NOT NULL,
 *      client_id integer NOT NULL
 */
@Entity
@Table(name = "user_client")
@SequenceGenerator(name = "transactionSequence", sequenceName = "user_client_user_client_id_seq", allocationSize = 1)
public class UserClientEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    // @JsonIgnore
    @Column(name = "user_client_id")
    private Long userClientId;

    @ManyToOne
    @MapsId("client_id")
    @JoinColumn(name = "client_id")
    ClientEntity client;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    UserEntity user;

    public ClientEntity getClient() {
        return client;
    }
    public void setClient(ClientEntity client) {
        this.client = client;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
}
