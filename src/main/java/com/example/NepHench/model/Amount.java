package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "amounts")
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amount_id")
    private Integer amountId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private User customerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceprovider_id")
    private User serviceProviderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Booking serviceId;


    @Column(name = "total_amount", precision = 10, scale = 2)
    private Double totalAmount;

}