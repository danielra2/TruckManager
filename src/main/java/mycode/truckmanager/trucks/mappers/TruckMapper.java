package mycode.truckmanager.trucks.mappers;

import mycode.truckmanager.trucks.dtos.DocStatus;
import mycode.truckmanager.trucks.dtos.DocumentDetail;
import mycode.truckmanager.trucks.dtos.TruckRequestDto;
import mycode.truckmanager.trucks.dtos.TruckResponseDto;
import mycode.truckmanager.trucks.models.Truck;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class TruckMapper {

    public Truck toEntity(TruckRequestDto dto) {
        return Truck.builder()
                .licensePlate(dto.licensePlate().trim().toUpperCase())
                .make(dto.make().trim())
                .model(dto.model().trim())
                .vgpExpiryDate(dto.vgpExpiryDate())
                .itvExpiryDate(dto.itvExpiryDate())
                .limitVExpiryDate(dto.limitVExpiryDate())
                .tGrafoExpiryDate(dto.tGrafoExpiryDate())
                .seguroExpiryDate(dto.seguroExpiryDate())
                .build();
    }

    public TruckResponseDto toResponseDto(Truck entity) {
        LocalDate today = LocalDate.now();

        DocumentDetail vgp = buildDocDetail(entity.getVgpExpiryDate(), today);
        DocumentDetail itv = buildDocDetail(entity.getItvExpiryDate(), today);
        DocumentDetail limitV = buildDocDetail(entity.getLimitVExpiryDate(), today);
        DocumentDetail tGrafo = buildDocDetail(entity.getTGrafoExpiryDate(), today);
        DocumentDetail seguro = buildDocDetail(entity.getSeguroExpiryDate(), today);

        DocStatus overall = DocStatus.VALID;
        if (vgp.status() == DocStatus.EXPIRED || itv.status() == DocStatus.EXPIRED ||
                limitV.status() == DocStatus.EXPIRED || tGrafo.status() == DocStatus.EXPIRED ||
                seguro.status() == DocStatus.EXPIRED) {
            overall = DocStatus.EXPIRED;
        } else if (vgp.status() == DocStatus.EXPIRING_SOON || itv.status() == DocStatus.EXPIRING_SOON ||
                limitV.status() == DocStatus.EXPIRING_SOON || tGrafo.status() == DocStatus.EXPIRING_SOON ||
                seguro.status() == DocStatus.EXPIRING_SOON) {
            overall = DocStatus.EXPIRING_SOON;
        }

        return new TruckResponseDto(
                entity.getId(),
                entity.getLicensePlate(),
                entity.getMake(),
                entity.getModel(),
                entity.getVgpExpiryDate(),
                entity.getItvExpiryDate(),
                entity.getLimitVExpiryDate(),
                entity.getTGrafoExpiryDate(),
                entity.getSeguroExpiryDate(),
                vgp,
                itv,
                limitV,
                tGrafo,
                seguro,
                overall
        );
    }

    private DocumentDetail buildDocDetail(LocalDate expiryDate, LocalDate today) {
        if (expiryDate == null) {
            return new DocumentDetail(null, -999L, DocStatus.EXPIRED);
        }
        long daysRemaining = ChronoUnit.DAYS.between(today, expiryDate);
        DocStatus status;
        if (daysRemaining < 0) {
            status = DocStatus.EXPIRED;
        } else if (daysRemaining <= 30) {
            status = DocStatus.EXPIRING_SOON;
        } else {
            status = DocStatus.VALID;
        }
        return new DocumentDetail(expiryDate, daysRemaining, status);
    }
}