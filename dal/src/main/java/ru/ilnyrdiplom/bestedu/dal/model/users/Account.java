package ru.ilnyrdiplom.bestedu.dal.model.users;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.facade.model.AccountFacade;

import javax.persistence.*;
import java.time.Instant;

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
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private String secondName;
    @Column(nullable = false)
    private String firstName;
    private String patronymic;
}
