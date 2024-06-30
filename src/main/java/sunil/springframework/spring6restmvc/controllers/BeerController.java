package sunil.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import sunil.springframework.spring6restmvc.services.BeerService;

@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;
}
