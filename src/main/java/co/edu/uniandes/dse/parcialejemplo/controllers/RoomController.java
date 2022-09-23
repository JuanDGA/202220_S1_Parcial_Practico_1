package co.edu.uniandes.dse.parcialejemplo.controllers;

import co.edu.uniandes.dse.parcialejemplo.dto.RoomDTO;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.services.RoomService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
  @NonNull
  private final RoomService roomService;

  @NonNull
  private final ModelMapper modelMapper;

  @PostMapping
  public RoomDTO createRoom(@RequestBody RoomDTO resource) throws IllegalOperationException {
    return modelMapper.map(
        roomService.createRoom(modelMapper.map(resource, RoomEntity.class)), RoomDTO.class);
  }
}
