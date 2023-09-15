package dev.ioexception.crawling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Builder
public class Lecture {

    @Id
    private String lecture_Id;

    private String title;
    private String instructor;
    private String company_name;
    private int view_count;
    private String ordinary_price;
    private String sale_price;
    private String sale_percent;
    private String description;
    private String image_link;
    private String site_link;

    public Lecture() {

    }

    public Lecture(String lecture_Id, String title, String instructor, String company_name, int view_count,
        String ordinary_price, String sale_price, String sale_percent, String description, String image_link,
        String site_link) {
        this.lecture_Id = lecture_Id;
        this.title = title;
        this.instructor = instructor;
        this.company_name = company_name;
        this.view_count = view_count;
        this.ordinary_price = ordinary_price;
        this.sale_price = sale_price;
        this.sale_percent = sale_percent;
        this.description = description;
        this.image_link = image_link;
        this.site_link = site_link;
    }
}
