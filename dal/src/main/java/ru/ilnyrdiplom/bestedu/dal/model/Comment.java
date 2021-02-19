package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private String text;
    private boolean isRemoved;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, updatable = false)
    private Account author;
    @ManyToOne
    @JoinColumn(name = "parent_id", updatable = false)
    private Comment parent;
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false, updatable = false)
    private Exercise exercise;
}
