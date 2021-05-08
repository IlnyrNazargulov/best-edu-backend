package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.facade.model.AccessDisciplineFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.AccessDisciplineStatus;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "access_discipline")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessDiscipline implements AccessDisciplineFacade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, updatable = false)
    private Account student;
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false, updatable = false)
    private Discipline discipline;
    @Setter
    @Enumerated(EnumType.STRING)
    private AccessDisciplineStatus status;

    public AccessDiscipline(Account student, Discipline discipline) {
        this.student = student;
        this.discipline = discipline;
        this.status = AccessDisciplineStatus.AWAIT;
    }
}
