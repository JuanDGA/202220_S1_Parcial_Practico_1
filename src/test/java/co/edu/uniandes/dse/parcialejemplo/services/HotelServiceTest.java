package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Transactional
@Import(HotelService.class)
@ExtendWith(SpringExtension.class)
public class HotelServiceTest {
  @Autowired
  private HotelService hotelService;

  @Autowired
  private TestEntityManager testEntityManager;

  private PodamFactory factory = new PodamFactoryImpl();

  @BeforeEach
  private void setUpTests() {
    clearData();
  }

  private void clearData() {
    testEntityManager.getEntityManager().createQuery("delete from HotelEntity");
  }

  @Test
  public void testCreateHotel() throws IllegalOperationException {
    HotelEntity hotel = factory.manufacturePojo(HotelEntity.class);

    hotel.setStars(3);

    HotelEntity createdHotel = hotelService.createHotel(hotel);

    assertEquals(createdHotel, hotel);

    IllegalOperationException duplicatedNameException =
        assertThrows(IllegalOperationException.class, () -> hotelService.createHotel(hotel));

    assertEquals("The hotel name is already in use.", duplicatedNameException.getMessage());

    HotelEntity hotelToThrown = factory.manufacturePojo(HotelEntity.class);

    hotelToThrown.setStars(123456789);

    IllegalOperationException wrongStarsException =
        assertThrows(IllegalOperationException.class, () -> hotelService.createHotel(hotelToThrown));

    assertEquals("Hotel's stars must be between 1 and 5.", wrongStarsException.getMessage());
  }
}
