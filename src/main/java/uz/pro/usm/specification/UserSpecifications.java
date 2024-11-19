package uz.pro.usm.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.pro.usm.domain.entity.user.User;

public class UserSpecifications {
    public static Specification<User> hasRoleName(String roleName) {
        return (root, query, criteriaBuilder) -> {
            if (roleName == null || roleName.isBlank()) {
                return null;
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.join("roles").get("name")),
                    roleName.toLowerCase()
            );
        };
    }

    public static Specification<User> sortByCreatedDate(Boolean sortCreationDate) {
        return (root, query, criteriaBuilder) -> {
            if (Boolean.TRUE.equals(sortCreationDate)) {
                query.orderBy(criteriaBuilder.asc(root.get("createdDate")));
            }
            return null;
        };
    }
}
