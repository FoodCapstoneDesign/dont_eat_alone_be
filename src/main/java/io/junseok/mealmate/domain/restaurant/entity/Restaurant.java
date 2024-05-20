package io.junseok.mealmate.domain.restaurant.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_image_url")
    private String restaurantImageUrl;

    @Column(name = "restaurant_file_name")
    private String restaurantFileName;

    @Column(name = "restaurant_type")
    private String restaurantType;

    @Column(name = "restaurant_notice")
    private String restaurantNotice;

    @Column(name = "location")
    private String location;

    @Column(name = "tel_num")
    private String telNum;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "grade")
    private Double grade;

    @Column(name = "open_at")
    private String openAt;

    @Column(name = "close_at")
    private String closeAt;
}
