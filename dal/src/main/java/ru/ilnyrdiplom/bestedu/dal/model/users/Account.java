package ru.ilnyrdiplom.bestedu.dal.model.users;

import lombok.*;
import org.hibernate.annotations.Formula;
import ru.ilnyrdiplom.bestedu.facade.model.AccountFacade;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "account")
@EqualsAndHashCode(of = {"id"})
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Account implements AccountFacade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private String login;
    @Setter
    @Column(nullable = false)
    private String passwordHash;
    @Setter
    @Column(nullable = false)
    private String secondName;
    @Setter
    @Column(nullable = false)
    private String firstName;
    @Setter
    private String patronymic;
    @Setter
    private LocalDate birthdate;
    @Setter
    private String rank;
    @Setter
    private String information;
    @Formula("(select count(di.id) from Discipline di where di.teacher_id = id and di.is_removed = false)")
    private Long countDisciplines;

    @Setter
    private boolean isRemoved;

    public Account(Instant createdAt, String login, String passwordHash, String secondName, String firstName, String patronymic) {
        this.createdAt = createdAt;
        this.login = login;
        this.passwordHash = passwordHash;
        this.secondName = secondName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }
}
