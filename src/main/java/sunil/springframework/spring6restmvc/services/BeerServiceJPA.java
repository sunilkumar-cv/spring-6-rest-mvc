package sunil.springframework.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import sunil.springframework.spring6restmvc.mappers.BeerMapper;
import sunil.springframework.spring6restmvc.model.BeerDTO;
import sunil.springframework.spring6restmvc.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return List.of();
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateBeerById(UUID id, BeerDTO beer) {

    }

    @Override
    public void deleteBeerById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
