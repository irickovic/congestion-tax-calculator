package hr.ent.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaxAccumulator {

    private int totalTax = 0;
    private int highestSingleCharge = 0;
    private LocalDateTime lastTaxedTime = null;

    public TaxAccumulator accumulate(Map.Entry<LocalDateTime, Integer> entry) {
        LocalDateTime currentTime = entry.getKey();
        int currentTax = entry.getValue();

        if (lastTaxedTime == null || currentTime.isAfter(lastTaxedTime.plusMinutes(60))) {
            totalTax += highestSingleCharge;
            highestSingleCharge = currentTax;
            lastTaxedTime = currentTime;
        } else {
            highestSingleCharge = Math.max(highestSingleCharge, currentTax);
        }

        return this;
    }

    public TaxAccumulator combine(TaxAccumulator other) {
        return this;
    }

    public int getTotalTax() {
        return Math.min(totalTax + highestSingleCharge, 60);
    }

}
