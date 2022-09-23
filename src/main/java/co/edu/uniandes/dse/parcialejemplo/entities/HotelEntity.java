package co.edu.uniandes.dse.parcialejemplo.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class HotelEntity extends BaseEntity {
  @Column(unique = true)
  private String name;
  private String address;
  private int stars;

  @PodamExclude
  @OneToMany(mappedBy = "hotel")
  private List<RoomEntity> rooms = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HotelEntity)) return false;
    if (!super.equals(o)) return false;
    HotelEntity that = (HotelEntity) o;
    return getStars() == that.getStars() && Objects.equals(getName(), that.getName()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getRooms(), that.getRooms());
  }
}
