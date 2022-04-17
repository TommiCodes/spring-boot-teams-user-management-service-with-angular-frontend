package com.usermanagement.model.specs;

import com.usermanagement.model.Team;
import com.usermanagement.model.Team_;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TeamSpecs {

    public static Specification<Team> nameLike(String name) {
      return new Specification<Team>() {
          @Override
          public Predicate toPredicate(@NotNull Root<Team> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
              return criteriaBuilder.like(root.get(Team_.name), '%' + name + '%');
          }
      };
    }

}
