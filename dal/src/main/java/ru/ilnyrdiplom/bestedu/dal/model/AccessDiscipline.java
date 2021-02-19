package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountStudent;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Entity
@Table(name = "access_discipline")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, updatable = false)
    private AccountStudent student;
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false, updatable = false)
    private Discipline discipline;
}
