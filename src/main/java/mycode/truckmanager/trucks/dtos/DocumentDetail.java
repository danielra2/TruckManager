package mycode.truckmanager.trucks.dtos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record DocumentDetail(
        LocalDate expiryDate,
        long daysRemaining,
        DocStatus status
) {
    public static DocumentDetail calculate(LocalDate date) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), date);
        DocStatus status;
        if (days <= 0) {
            status = DocStatus.EXPIRED;
        } else if (days <= 30) {
            status = DocStatus.EXPIRING_SOON;
        } else {
            status = DocStatus.VALID;
        }
        return new DocumentDetail(date, days, status);
    }
}