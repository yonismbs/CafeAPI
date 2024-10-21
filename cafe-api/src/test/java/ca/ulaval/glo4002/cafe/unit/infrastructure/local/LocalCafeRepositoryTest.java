package ca.ulaval.glo4002.cafe.unit.infrastructure.local;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeId;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalCafeRepository;

class LocalCafeRepositoryTest {

    private Cafe cafe;
    private CafeId id;

    @BeforeEach
    void setup() {
        cafe = mock(Cafe.class);
        id = mock(CafeId.class);
    }

    @Test
    void givenSavedFranchise_whenFindingFranchise_thenReturnSavedFranchise() {
        LocalCafeRepository localFranchiseRepository = new LocalCafeRepository();

        localFranchiseRepository.save(id, cafe);

        assertEquals(cafe, localFranchiseRepository.find(id));
    }

}