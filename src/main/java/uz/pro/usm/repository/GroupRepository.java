package uz.pro.usm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pro.usm.domain.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
}
