package hr.ent.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaxInterval {

    private LocalTime start;
    private LocalTime end;
    private int tax;

}
