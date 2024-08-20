package uz.pro.usm.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pro.usm.domain.entity.user.RolePermission;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<RolePermission,Long> {
}
