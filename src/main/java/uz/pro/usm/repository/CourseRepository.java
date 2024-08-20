package uz.pro.usm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pro.usm.domain.entity.course.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
}
