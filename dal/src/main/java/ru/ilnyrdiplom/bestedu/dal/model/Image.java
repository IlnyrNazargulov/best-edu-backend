package ru.ilnyrdiplom.bestedu.dal.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.facade.model.ImageFacade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image implements Serializable, ImageFacade {
    @Id
    private UUID fileUuid;
    @MapsId
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "file_uuid", unique = true, nullable = false)
    private File file;
    @Basic(optional = false)
    private String mimeType;

    public Image(File file, String mimeType) {
        this.file = file;
        this.mimeType = mimeType;
    }
}
