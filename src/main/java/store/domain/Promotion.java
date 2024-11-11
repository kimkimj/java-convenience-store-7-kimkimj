package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private int minimumQuantity;
    private int givenForFree;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int minimumQuantity, int givenForFree, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.minimumQuantity = minimumQuantity;
        this.givenForFree = givenForFree;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public int getGivenForFree() {
        return givenForFree;
    }

    public boolean isValid() {
        return DateTimes.now().isAfter(startDate.atStartOfDay()) && DateTimes.now().isBefore(endDate.atTime(23, 59, 59));
    }
}
