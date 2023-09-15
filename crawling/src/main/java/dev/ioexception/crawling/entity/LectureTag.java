package dev.ioexception.crawling.entity;

import static jakarta.persistence.FetchType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LectureTag {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lectag_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;


}
