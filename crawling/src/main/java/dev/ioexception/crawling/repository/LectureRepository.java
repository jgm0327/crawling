package dev.ioexception.crawling.repository;

import java.util.Optional;

import dev.ioexception.crawling.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
	Optional<Lecture> findLectureByLectureId(String lectureId);
}
