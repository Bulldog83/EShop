package ru.bulldog.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bulldog.eshop.model.Picture;

@Repository
public interface PictureRepo extends JpaRepository<Picture, Long> {
}
