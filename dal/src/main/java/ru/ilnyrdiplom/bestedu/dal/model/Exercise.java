package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.*;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFacade;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Entity
@Table(name = "exercise")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise implements ExerciseFacade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @Setter
    @Column(nullable = false)
    private String name;
    @Setter
    private boolean isRemoved = false;
    @Setter
    private int orderNumber;
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false, updatable = false)
    private Discipline discipline;
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    private ExerciseFile content;

    @Builder
    public Exercise(Instant createdAt, String name, int orderNumber, Discipline discipline, ExerciseFile content) {
        this.createdAt = createdAt;
        this.name = name;
        this.orderNumber = orderNumber;
        this.discipline = discipline;
        this.content = content;
    }
}
