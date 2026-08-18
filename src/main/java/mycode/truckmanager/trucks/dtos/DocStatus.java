package mycode.truckmanager.trucks.dtos;

public enum DocStatus {
    EXPIRED,        // ROSU (<= 0 zile)
    EXPIRING_SOON,  // PORTOCALIU (1 - 30 zile)
    VALID           // VERDE (> 30 zile)
}