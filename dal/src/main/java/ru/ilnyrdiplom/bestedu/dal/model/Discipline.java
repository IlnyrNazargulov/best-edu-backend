package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.facade.model.DisciplineFacade;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Entity
@Table(name = "discipline")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discipline implements DisciplineFacade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false, updatable = false)
    private AccountTeacher teacher;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @Setter
    @Column(nullable = false)
    private String name;
    @Setter
    private boolean isVisible = true;
    @Setter
    private boolean isRemoved = false;
    @Setter
    private boolean isPublic = true;
    @Setter
    @Column(nullable = false)
    private String description;
    @Transient
    @Setter
    private boolean isAccess;

    public Discipline(AccountTeacher teacher, Instant createdAt, String name, boolean isPublic, String description) {
        this.teacher = teacher;
        this.createdAt = createdAt;
        this.name = name;
        this.isPublic = isPublic;
        this.description = description;
    }
}
