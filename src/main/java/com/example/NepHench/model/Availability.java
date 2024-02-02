package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "service_provider_id", nullable = false)
    private User serviceProvider;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "\"time\"", nullable = false)
    private LocalTime time;

    @Size(max = 10)
    @NotNull
    @Column(name = "availability_status", nullable = false, length = 10)
    private String availabilityStatus = "booked";

    @Column(name = "max_bookings")
    private Integer maxBookings;

    @Column(name = "additional_notes")
    @Type(type = "org.hibernate.type.TextType")
    private String additionalNotes;

}