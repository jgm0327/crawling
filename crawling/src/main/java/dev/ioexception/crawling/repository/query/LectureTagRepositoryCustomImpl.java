package dev.ioexception.crawling.repository.query;

import com.querydsl.core.BooleanBuilder;
import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.entity.LectureTag;
import dev.ioexception.crawling.entity.QLecture;
import dev.ioexception.crawling.entity.QLectureTag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class LectureTagRepositoryCustomImpl extends QuerydslRepositorySupport implements LectureTagRepositoryCustom {

    public LectureTagRepositoryCustomImpl() {
        super(LectureTag.class);
    }

    public List<LectureTag> getLectureTags(String lectureId) {
        QLecture lecture = QLecture.lecture;
        QLectureTag lectureTag = QLectureTag.lectureTag;


        return from(lectureTag)
                .leftJoin(lectureTag.lecture, lecture)
                .fetchJoin()
                .where(lectureTag.lecture.lectureId.eq(lectureId)
                        .and(lecture.date.eq(LocalDate.now())))
                .fetch();
    }
}
