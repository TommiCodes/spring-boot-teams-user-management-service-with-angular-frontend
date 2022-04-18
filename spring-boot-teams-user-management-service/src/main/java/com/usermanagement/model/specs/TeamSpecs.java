package com.usermanagement.model.specs;

import com.usermanagement.model.Team;
import com.usermanagement.model.Team_;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class TeamSpecs {

    public static Specification<Team> nameLike(String name) {
      return new Specification<Team>() {
          @Override
          public Predicate toPredicate(@NotNull Root<Team> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
              return criteriaBuilder.like(criteriaBuilder.lower(root.get(Team_.name)), '%' + name.toLowerCase() + '%');
          }
      };
    }

}
