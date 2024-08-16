package uz.pro.usm.domain.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pro.usm.domain.entity.BaseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "permission_entity")
public class RolePermission extends BaseEntity {
    @Column(unique = true,nullable = false)
    private String name;
}
