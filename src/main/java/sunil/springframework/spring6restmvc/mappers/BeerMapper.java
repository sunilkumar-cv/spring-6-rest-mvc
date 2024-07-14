package sunil.springframework.spring6restmvc.mappers;

import org.mapstruct.Mapper;
import sunil.springframework.spring6restmvc.entities.Beer;
import sunil.springframework.spring6restmvc.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDTO(Beer beer);
}
