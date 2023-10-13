package dev.ioexception.crawling.repository;

import java.util.List;
import java.util.Optional;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.entity.LectureTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureTagRepository extends JpaRepository<LectureTag, Long> {
	List<LectureTag> findAllByLecture_LectureId(String lectureId);
}
