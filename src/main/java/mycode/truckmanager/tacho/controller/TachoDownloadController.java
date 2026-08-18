package mycode.truckmanager.tacho.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mycode.truckmanager.tacho.dtos.TachoDownloadRequestDto;
import mycode.truckmanager.tacho.dtos.TachoDownloadResponseDto;
import mycode.truckmanager.tacho.service.TachoDownloadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tacho-downloads")
@RequiredArgsConstructor
public class TachoDownloadController {

    private final TachoDownloadService service;

    @GetMapping
    public ResponseEntity<List<TachoDownloadResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAllDownloads());
    }

    @PostMapping
    public ResponseEntity<TachoDownloadResponseDto> create(@Valid @RequestBody TachoDownloadRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDownload(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TachoDownloadResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody TachoDownloadRequestDto request
    ) {
        return ResponseEntity.ok(service.updateDownload(id, request));
    }

    @PatchMapping("/{id}/download-today")
    public ResponseEntity<TachoDownloadResponseDto> markToday(@PathVariable Long id) {
        return ResponseEntity.ok(service.markDownloadedToday(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDownload(id);
        return ResponseEntity.noContent().build();
    }
}