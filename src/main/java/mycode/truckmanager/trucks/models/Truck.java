package mycode.truckmanager.trucks.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(name = "license_plate", nullable = false, unique = true, length = 20)
    private String licensePlate;

    @Column(nullable = false, length = 50)
    private String make;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(name = "itp_expiry_date", nullable = false)
    private LocalDate itpExpiryDate;

    @Column(name = "insurance_expiry_date", nullable = false)
    private LocalDate insuranceExpiryDate;

    @Column(name = "tacho_expiry_date", nullable = false)
    private LocalDate tachoExpiryDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}