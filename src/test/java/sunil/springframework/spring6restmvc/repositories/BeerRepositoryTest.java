package sunil.springframework.spring6restmvc.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import sunil.springframework.spring6restmvc.bootstrap.BootStrapData;
import sunil.springframework.spring6restmvc.entities.Beer;
import sunil.springframework.spring6restmvc.model.BeerStyle;
import sunil.springframework.spring6restmvc.services.BeerCsvServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootStrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("New Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("34345")
                        .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testGetBeerListByBeerStyle() {
        Page<Beer> page = beerRepository.findAllByBeerStyle(BeerStyle.IPA, null);

        assertThat(page.getContent().size()).isEqualTo(547);
    }

    @Test
    void testGetBeerListByName() {
        Page<Beer> page = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);

        assertThat(page.getContent().size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("New Beer rwegfdfrwegfdfrwegfdfrwegfdfrwegfdfrwegfdfrwegfdfrwegfdfrwegfdfrwegfdfrwegfdfrwegfdf")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("34345")
                    .price(new BigDecimal("11.99"))
                    .build());

            beerRepository.flush();
        });
    }
}