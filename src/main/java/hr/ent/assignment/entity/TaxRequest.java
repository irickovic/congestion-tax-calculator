package hr.ent.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaxRequest {

    private String vehicleType;
    private List<LocalDateTime> passages;

}
