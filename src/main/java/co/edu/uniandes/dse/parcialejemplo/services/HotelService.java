package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {
  @NonNull
  private final HotelRepository repository;

  @Transactional
  public HotelEntity createHotel(HotelEntity resource) throws IllegalOperationException {
    Optional<HotelEntity> existentName = repository.findHotelEntityByName(resource.getName());

    if (existentName.isPresent())
      throw new IllegalOperationException("The hotel name is already in use.");

    if (resource.getStars() > 5 || resource.getStars() < 1)
      throw new IllegalOperationException("Hotel's stars must be between 1 and 5.");

    return repository.save(resource);
  }
}
