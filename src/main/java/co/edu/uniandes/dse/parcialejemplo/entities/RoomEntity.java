package co.edu.uniandes.dse.parcialejemplo.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
}
