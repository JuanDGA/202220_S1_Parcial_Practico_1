package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;
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
@Import(RoomService.class)
@ExtendWith(SpringExtension.class)
public class RoomServiceTest {
  @Autowired
  private RoomService roomService;

  @Autowired
  private TestEntityManager testEntityManager;

  private PodamFactory factory = new PodamFactoryImpl();

  @BeforeEach
  private void setUpTests() {
    clearData();
  }

  private void clearData() {
    testEntityManager.getEntityManager().createQuery("delete from RoomEntity ");
  }

  @Test
  public void testCreateRoom() throws IllegalOperationException {
    RoomEntity room = factory.manufacturePojo(RoomEntity.class);

    room.setNumber(3);
    room.setBeds(3);

    RoomEntity createdRoom = roomService.createRoom(room);

    assertEquals(createdRoom, room);
  }

  @Test
  public void testCreateRoomExceptions() {
    RoomEntity room = factory.manufacturePojo(RoomEntity.class);
    room.setNumber(5);
    room.setBeds(3);

    assertThrows(IllegalOperationException.class, () -> roomService.createRoom(room));
  }
}
