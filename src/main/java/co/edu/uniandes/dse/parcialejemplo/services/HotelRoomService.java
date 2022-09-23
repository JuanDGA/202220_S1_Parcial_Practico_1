package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.RoomRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelRoomService {
  @NonNull
  private final HotelRepository hotelRepository;

  @NonNull
  private final RoomRepository roomRepository;

  @Transactional
  public void addRoom(Long hotelId, Long roomId) throws EntityNotFoundException {
    Optional<HotelEntity> optionalHotel = hotelRepository.findById(hotelId);

    if (optionalHotel.isEmpty())
      throw new EntityNotFoundException("Hotel not found");

    Optional<RoomEntity> optionalRoom = roomRepository.findById(roomId);

    if (optionalRoom.isEmpty())
      throw new EntityNotFoundException("Room not found");

    HotelEntity hotel = optionalHotel.get();
    RoomEntity room = optionalRoom.get();

    hotel.getRooms().add(room);
    room.setHotel(hotel);

    hotelRepository.save(hotel);
    roomRepository.save(room);
  }
}
