package mycode.truckmanager.trucks.mappers;

import mycode.truckmanager.trucks.dtos.DocStatus;
import mycode.truckmanager.trucks.dtos.DocumentDetail;
import mycode.truckmanager.trucks.dtos.TruckRequestDto;
import mycode.truckmanager.trucks.dtos.TruckResponseDto;
import mycode.truckmanager.trucks.models.Truck;
import org.springframework.stereotype.Component;

@Component
public class TruckMapper {

    public Truck toEntity(TruckRequestDto dto) {
        return Truck.builder()
                .licensePlate(dto.licensePlate().trim().toUpperCase())
                .make(dto.make().trim())
                .model(dto.model().trim())
                .itpExpiryDate(dto.itpExpiryDate())
                .insuranceExpiryDate(dto.insuranceExpiryDate())
                .tachoExpiryDate(dto.tachoExpiryDate())
                .build();
    }

    public TruckResponseDto toResponseDto(Truck truck) {
        DocumentDetail itp = DocumentDetail.calculate(truck.getItpExpiryDate());
        DocumentDetail insurance = DocumentDetail.calculate(truck.getInsuranceExpiryDate());
        DocumentDetail tacho = DocumentDetail.calculate(truck.getTachoExpiryDate());

        DocStatus overall = DocStatus.VALID;
        if (itp.status() == DocStatus.EXPIRED || insurance.status() == DocStatus.EXPIRED || tacho.status() == DocStatus.EXPIRED) {
            overall = DocStatus.EXPIRED;
        } else if (itp.status() == DocStatus.EXPIRING_SOON || insurance.status() == DocStatus.EXPIRING_SOON || tacho.status() == DocStatus.EXPIRING_SOON) {
            overall = DocStatus.EXPIRING_SOON;
        }

        return new TruckResponseDto(
                truck.getId(),
                truck.getLicensePlate(),
                truck.getMake(),
                truck.getModel(),
                itp,
                insurance,
                tacho,
                overall,
                truck.getCreatedAt(),
                truck.getUpdatedAt()
        );
    }
}