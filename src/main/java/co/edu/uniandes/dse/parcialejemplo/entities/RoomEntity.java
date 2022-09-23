package co.edu.uniandes.dse.parcialejemplo.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
public class RoomEntity extends BaseEntity {
  private int number;
  private int capacity;
  private int beds;
  private int bathrooms;

  @PodamExclude
  @ManyToOne
  private HotelEntity hotel;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoomEntity)) return false;
    if (!super.equals(o)) return false;
    RoomEntity room = (RoomEntity) o;
    return getNumber() == room.getNumber() && getCapacity() == room.getCapacity() && getBeds() == room.getBeds() && getBathrooms() == room.getBathrooms() && Objects.equals(getHotel(), room.getHotel());
  }
}
