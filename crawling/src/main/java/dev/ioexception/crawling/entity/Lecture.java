package dev.ioexception.crawling.entity;

import java.time.LocalDate;
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
import lombok.Data;
import lombok.Getter;

@Data
@Entity
public class Lecture {

    @Id
    private String lecture_Id;
    private String title;
    private String instructor;
    private String company_name;
    private int ordinary_price;
    private int sale_price;
    private String sale_percent;
    private String image_link;
    private String site_link;
    private LocalDate date;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureTag> lectureTags = new ArrayList<>();

    public Lecture() {

    }
    @Builder

    public Lecture(String lecture_Id, String title, String instructor, String company_name, int ordinary_price,
                   int sale_price, String sale_percent, String image_link, String site_link, LocalDate date) {
        this.lecture_Id = lecture_Id;
        this.title = title;
        this.instructor = instructor;
        this.company_name = company_name;
        this.ordinary_price = ordinary_price;
        this.sale_price = sale_price;
        this.sale_percent = sale_percent;
        this.image_link = image_link;
        this.site_link = site_link;
        this.date = date;
    }
}
