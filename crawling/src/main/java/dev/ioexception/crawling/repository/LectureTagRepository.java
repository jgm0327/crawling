package dev.ioexception.crawling.repository;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.entity.LectureTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureTagRepository extends JpaRepository<LectureTag, Long> {
}
