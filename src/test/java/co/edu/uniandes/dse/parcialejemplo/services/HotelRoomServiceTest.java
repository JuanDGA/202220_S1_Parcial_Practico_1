package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Transactional
@Import(HotelRoomService.class)
@ExtendWith(SpringExtension.class)
public class HotelRoomServiceTest {
  @Autowired
  private HotelRoomService hotelRoomService;

  @Autowired
  private TestEntityManager testEntityManager;

  private PodamFactory factory = new PodamFactoryImpl();

  private List<HotelEntity> hotels = new ArrayList<>();

  @BeforeEach
  private void setUpTests() {
    clearData();
  }

  private void clearData() {
    testEntityManager.getEntityManager().createQuery("delete from HotelEntity");
    testEntityManager.getEntityManager().createQuery("delete from RoomEntity");
  }

  private void insertData() {
    for (int i = 0; i < 3; i++) {
      HotelEntity hotel = factory.manufacturePojo(HotelEntity.class);
      testEntityManager.persist(hotel);
      hotels.add(hotel);
    }
  }
}
