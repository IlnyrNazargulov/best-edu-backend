package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.*;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.facade.model.CommentFacade;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment implements CommentFacade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private String text;
    @Setter
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

    @Builder
    public Comment(Instant createdAt, String text, Account author, Comment parent, Exercise exercise) {
        this.createdAt = createdAt;
        this.text = text;
        this.author = author;
        this.parent = parent;
        this.exercise = exercise;
    }

    @Override
    public Integer getParentId() {
        return parent != null ? parent.getId() : null;
    }
}
