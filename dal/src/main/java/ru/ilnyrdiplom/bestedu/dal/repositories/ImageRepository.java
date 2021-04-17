package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.Image;

import java.util.UUID;

public interface ImageRepository extends CrudRepository<Image, UUID> {
}
