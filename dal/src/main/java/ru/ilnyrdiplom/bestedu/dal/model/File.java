package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.facade.model.FileFacade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "file")
@EqualsAndHashCode(of = {"uuid"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File implements Serializable, FileFacade {
    @Id
    private UUID uuid;
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private Account owner;
    @Column(updatable = false)
    private boolean isRemoved = false;
    private String name;
    private String extension;
    private long size;

    public File(UUID uuid, Account owner, String name, String extension, Instant createdAt, long size) {
        this.owner = owner;
        this.name = name;
        this.uuid = uuid;
        this.extension = extension;
        this.createdAt = createdAt;
        this.size = size;
    }

    public String getFileName() {
        return uuid + "." + extension;
    }
}
