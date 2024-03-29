package start;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
}
