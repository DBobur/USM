package uz.pro.usm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pro.usm.domain.entity.user.UserRole;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<UserRole,Long> {
}
