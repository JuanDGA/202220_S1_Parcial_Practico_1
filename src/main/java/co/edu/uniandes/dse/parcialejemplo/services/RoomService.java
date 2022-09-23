package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.RoomRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {
  @NonNull
  private final RoomRepository roomRepository;

  @Transactional
  public RoomEntity createRoom(RoomEntity resource) throws IllegalOperationException {
    if (resource.getNumber() > resource.getBeds())
      throw new IllegalOperationException("The room identification number must be minor or equals than the number of beds.");

    return roomRepository.save(resource);
  }
}
