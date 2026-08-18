package mycode.truckmanager.tacho.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tacho_downloads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TachoDownload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "last_download_date", nullable = false)
    private LocalDate lastDownloadDate;

    @Column(name = "next_download_date", nullable = false)
    private LocalDate nextDownloadDate;
}