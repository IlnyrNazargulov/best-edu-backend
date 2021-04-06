package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;

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
    @Setter
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
    @Setter
    @Enumerated(EnumType.STRING)
    private ExerciseFileType exerciseFileType;

    public ExerciseFile(File file, Exercise exercise, ExerciseFileType exerciseFileType) {
        this.file = file;
        this.exercise = exercise;
        this.exerciseFileType = exerciseFileType;
    }

    public ExerciseFile(File file) {
        this.file = file;
    }
}
