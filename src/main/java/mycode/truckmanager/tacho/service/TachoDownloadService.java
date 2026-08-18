package mycode.truckmanager.tacho.service;

import mycode.truckmanager.tacho.dtos.TachoDownloadRequestDto;
import mycode.truckmanager.tacho.dtos.TachoDownloadResponseDto;

import java.util.List;

public interface TachoDownloadService {
    List<TachoDownloadResponseDto> getAllDownloads();
    TachoDownloadResponseDto createDownload(TachoDownloadRequestDto dto);
    TachoDownloadResponseDto updateDownload(Long id, TachoDownloadRequestDto dto);
    TachoDownloadResponseDto markDownloadedToday(Long id);
    void deleteDownload(Long id);
}