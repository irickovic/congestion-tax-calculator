package hr.ent.assignment.controller;

import hr.ent.assignment.entity.TaxRequest;
import hr.ent.assignment.service.CongestionTaxService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tax")
public class CongestionTaxController {

    private final CongestionTaxService taxService;

    public CongestionTaxController(CongestionTaxService taxService) {
        this.taxService = taxService;
    }

    @PostMapping("/gothenburg/calculate")
    public int calculateTax(@RequestBody TaxRequest request) {
        return taxService.getTax(request.getVehicleType(), request.getPassages());
    }

}
