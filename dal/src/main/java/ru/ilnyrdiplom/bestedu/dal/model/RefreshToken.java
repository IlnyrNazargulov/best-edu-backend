package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    private UUID token;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @Column(updatable = false, nullable = false)
    private Instant expiredAt;
}
