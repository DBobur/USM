package uz.pro.usm.domain.entity.course;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.pro.usm.domain.entity.BaseEntity;

import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "courses")
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "lessons")
public class Course extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String title;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lesson> lessons;
}
