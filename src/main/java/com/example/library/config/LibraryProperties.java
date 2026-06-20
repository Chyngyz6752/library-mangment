package com.example.library.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

/**
 * Конфигурационные свойства библиотеки (префикс library.loan):
 * срок выдачи в днях и штраф за день просрочки. Читаются из application.properties.
 */

@ConfigurationProperties(prefix = "library.loan")
public class LibraryProperties {

    private int durationDays = 14;
    private BigDecimal finePerDay = new BigDecimal("0.50");

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public BigDecimal getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(BigDecimal finePerDay) {
        this.finePerDay = finePerDay;
    }
}
