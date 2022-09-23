package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  private List<RoomEntity> rooms = new ArrayList<>();

  @BeforeEach
  private void setUpTests() {
    clearData();
    insertData();
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

    for (int j = 0; j < 3; j++) {
      RoomEntity room = factory.manufacturePojo(RoomEntity.class);
      testEntityManager.persist(room);
      rooms.add(room);
    }
  }

  @Test
  public void testAddRoomToHotel() throws EntityNotFoundException {
    for (int i = 0; i < 3; i++) {
      HotelEntity hotel = hotels.get(i);
      RoomEntity room = rooms.get(i);

      hotelRoomService.addRoom(hotel.getId(), room.getId());

      HotelEntity hotelFound = testEntityManager.find(HotelEntity.class, hotel.getId());

      assertTrue(hotelFound.getRooms().contains(room));
    }
  }

  @Test
  public void testAddRoomToNoExistentHotel() {
    for (RoomEntity room: rooms) {
      EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
          () -> hotelRoomService.addRoom(Long.parseLong("123456789"), room.getId()));

      assertEquals("Hotel not found", exception.getMessage());
    }
  }

  @Test
  public void testAddNoExistentRoomHotel() {
    for (HotelEntity hotel: hotels) {
      EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
          () -> hotelRoomService.addRoom(hotel.getId(), Long.parseLong("123456789")));

      assertEquals("Room not found", exception.getMessage());
    }
  }
}
