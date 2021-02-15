package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Entity
@Table(name = "exercise_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseFile implements ExerciseFileFacade, Serializable {
    @Id
    private UUID fileUuid;
    @MapsId
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "file_uuid", unique = true, nullable = false)
    private File file;
    @Basic(optional = false)
    private String mimeType;
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false, updatable = false)
    private Exercise exercise;

    public ExerciseFile(File file, String mimeType) {
        this.file = file;
        this.mimeType = mimeType;
    }
}
