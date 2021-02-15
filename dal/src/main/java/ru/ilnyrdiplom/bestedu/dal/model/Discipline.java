package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Entity
@Table(name = "discipline")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false, updatable = false)
    private AccountTeacher teacher;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private String name;
    private boolean isRemoved;
}
