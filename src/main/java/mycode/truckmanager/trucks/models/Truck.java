package mycode.truckmanager.trucks.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "trucks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "vgp_expiry_date")
    private LocalDate vgpExpiryDate;

    @Column(name = "itv_expiry_date")
    private LocalDate itvExpiryDate;

    @Column(name = "limit_v_expiry_date")
    private LocalDate limitVExpiryDate;

    @Column(name = "t_grafo_expiry_date")
    private LocalDate tGrafoExpiryDate;

    @Column(name = "seguro_expiry_date")
    private LocalDate seguroExpiryDate;
}