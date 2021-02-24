package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;

import javax.persistence.*;

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
    private Account student;
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false, updatable = false)
    private Discipline discipline;
}
