package mycode.truckmanager.tacho.service;

import lombok.RequiredArgsConstructor;
import mycode.truckmanager.tacho.dtos.TachoDownloadRequestDto;
import mycode.truckmanager.tacho.dtos.TachoDownloadResponseDto;
import mycode.truckmanager.tacho.models.TachoDownload;
import mycode.truckmanager.tacho.repository.TachoDownloadRepository;
import mycode.truckmanager.trucks.dtos.DocStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TachoDownloadServiceImpl implements TachoDownloadService {

    private final TachoDownloadRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<TachoDownloadResponseDto> getAllDownloads() {
        return repository.findAllByOrderByNextDownloadDateAsc()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public TachoDownloadResponseDto createDownload(TachoDownloadRequestDto dto) {
        String cleanPlate = dto.licensePlate().trim().toUpperCase();
        LocalDate nextDate = dto.nextDownloadDate() != null
                ? dto.nextDownloadDate()
                : dto.lastDownloadDate().plusDays(28);

        TachoDownload entity = TachoDownload.builder()
                .licensePlate(cleanPlate)
                .driverName(dto.driverName() != null ? dto.driverName().trim() : "")
                .lastDownloadDate(dto.lastDownloadDate())
                .nextDownloadDate(nextDate)
                .build();

        return toResponseDto(repository.save(entity));
    }

    @Override
    public TachoDownloadResponseDto updateDownload(Long id, TachoDownloadRequestDto dto) {
        TachoDownload entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Înregistrarea cu ID-ul " + id + " nu există."));

        LocalDate nextDate = dto.nextDownloadDate() != null
                ? dto.nextDownloadDate()
                : dto.lastDownloadDate().plusDays(28);

        entity.setLicensePlate(dto.licensePlate().trim().toUpperCase());
        entity.setDriverName(dto.driverName() != null ? dto.driverName().trim() : "");
        entity.setLastDownloadDate(dto.lastDownloadDate());
        entity.setNextDownloadDate(nextDate);

        return toResponseDto(repository.save(entity));
    }

    @Override
    public TachoDownloadResponseDto markDownloadedToday(Long id) {
        TachoDownload entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Înregistrarea cu ID-ul " + id + " nu există."));

        LocalDate today = LocalDate.now();
        entity.setLastDownloadDate(today);
        entity.setNextDownloadDate(today.plusDays(28));

        return toResponseDto(repository.save(entity));
    }

    @Override
    public void deleteDownload(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Înregistrarea nu există.");
        }
        repository.deleteById(id);
    }

    private TachoDownloadResponseDto toResponseDto(TachoDownload entity) {
        LocalDate today = LocalDate.now();
        long daysRemaining = ChronoUnit.DAYS.between(today, entity.getNextDownloadDate());

        DocStatus status;
        if (daysRemaining <= 3) {
            status = DocStatus.EXPIRED;       // Roșu (<= 3 zile sau expirat)
        } else if (daysRemaining <= 7) {
            status = DocStatus.EXPIRING_SOON; // Portocaliu (4-7 zile)
        } else {
            status = DocStatus.VALID;         // Verde (> 7 zile)
        }

        return new TachoDownloadResponseDto(
                entity.getId(),
                entity.getLicensePlate(),
                entity.getDriverName(),
                entity.getLastDownloadDate(),
                entity.getNextDownloadDate(),
                daysRemaining,
                status
        );
    }
}