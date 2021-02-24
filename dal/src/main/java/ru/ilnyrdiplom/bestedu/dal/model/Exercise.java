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
    private String content;
    @Setter
    private boolean isRemoved = false;
    @Setter
    private int orderNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id", nullable = false, updatable = false)
    private Discipline discipline;

    @Builder
    public Exercise(Instant createdAt, String name, String content, int orderNumber, Discipline discipline) {
        this.createdAt = createdAt;
        this.name = name;
        this.content = content;
        this.orderNumber = orderNumber;
        this.discipline = discipline;
    }
}
