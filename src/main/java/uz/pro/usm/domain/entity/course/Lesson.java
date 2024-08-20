package uz.pro.usm.domain.entity.course;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pro.usm.domain.entity.BaseEntity;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lessons")
@EqualsAndHashCode(callSuper = true)
public class Lesson extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}

