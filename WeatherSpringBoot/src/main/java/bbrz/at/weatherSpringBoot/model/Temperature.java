package bbrz.at.weatherSpringBoot.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "temperatures")
public class Temperature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String Location;
    private long timestamp;
    private double celcius;
}
