package com.usermanagement.model.specs;

import com.usermanagement.model.Team;
import com.usermanagement.model.Team_;
import com.usermanagement.model.User;
import com.usermanagement.model.User_;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecs {

    public static Specification<User> usernameLike(String username) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(@NotNull Root<User> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(User_.username)), '%' + username.toLowerCase() + '%');
            }
        };
    }
}
