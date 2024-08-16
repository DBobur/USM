package uz.pro.usm.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import uz.pro.usm.domain.entity.BaseEntity;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "role_entity")
public class UserRole extends BaseEntity {
    @Column(unique = true,nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<RolePermission> permissions;
}
