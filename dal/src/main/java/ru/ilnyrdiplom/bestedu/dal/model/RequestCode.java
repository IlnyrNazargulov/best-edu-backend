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
@Table(name = "request_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false, nullable = false)
    private String email;
    @Column(updatable = false, nullable = false)
    private String code;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    public RequestCode(String email, String code, Instant createdAt) {
        this.email = email;
        this.code = code;
        this.createdAt = createdAt;
    }
}
