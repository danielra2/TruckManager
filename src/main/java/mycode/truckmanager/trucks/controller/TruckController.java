package mycode.truckmanager.trucks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mycode.truckmanager.trucks.dtos.TruckRequestDto;
import mycode.truckmanager.trucks.dtos.TruckResponseDto;
import mycode.truckmanager.trucks.service.TruckCommandService;
import mycode.truckmanager.trucks.service.TruckQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TruckController {

    private final TruckQueryService truckQueryService;
    private final TruckCommandService truckCommandService;

    @GetMapping
    public ResponseEntity<List<TruckResponseDto>> getAllTrucks() {
        return ResponseEntity.ok(truckQueryService.getAllTrucks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckResponseDto> getTruckById(@PathVariable Long id) {
        return ResponseEntity.ok(truckQueryService.getTruckById(id));
    }

    @PostMapping
    public ResponseEntity<TruckResponseDto> createTruck(@Valid @RequestBody TruckRequestDto request) {
        return new ResponseEntity<>(truckCommandService.createTruck(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TruckResponseDto> updateTruck(@PathVariable Long id, @Valid @RequestBody TruckRequestDto request) {
        return ResponseEntity.ok(truckCommandService.updateTruck(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        truckCommandService.deleteTruck(id);
        return ResponseEntity.noContent().build();
    }
}