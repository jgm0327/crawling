package dev.ioexception.crawling.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Tag {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tag_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
	private List<LectureTag> lectureTags = new ArrayList<>();

	public Tag() {

	}

	//연관관계 메서드 작성
}