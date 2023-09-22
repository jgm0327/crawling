package dev.ioexception.crawling.entity;

import static jakarta.persistence.FetchType.*;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LectureTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectagId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lectureId", referencedColumnName = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tagId")
    private Tag tag;


    public void setLecture(Lecture lecture){
        this.lecture = lecture;
        lecture.getLectureTags().add(this);
    }

    public void setTag(Tag tag){
        this.tag = tag;
        tag.getLectureTags().add(this);
    }
}