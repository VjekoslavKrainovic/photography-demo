package com.photography.demo.adapter.out.persistance.repository;

import com.photography.demo.adapter.out.persistance.entity.PhotographyDbo;
import com.photography.demo.domain.search.ComparisonOperator;
import com.photography.demo.domain.search.Field;
import com.photography.demo.domain.search.FieldName;
import com.photography.demo.domain.search.LogicalOperator;
import com.photography.demo.domain.search.SearchPhotography;
import com.photography.demo.domain.search.SubQuery;
import org.springframework.data.jpa.domain.Specification;


public class PhotographySpecification {

  public static Specification<PhotographyDbo> createSearch(SearchPhotography searchPhotography) {
    Specification<PhotographyDbo> specificationQuery = Specification.where(null);

    if (searchPhotography.getSubQueries().isEmpty()) {
      return specificationQuery;
    }

    for (SubQuery subQuery : searchPhotography.getSubQueries()) {
      Specification<PhotographyDbo> specificationSubQuery = createSubQuery(subQuery);
      if (subQuery.getLogicalOperator() == null) {
        return specificationQuery.and(specificationSubQuery); // TODO: fix this, trebas uzet od prijasnjeg subquerya logicne operatore a ne od trenutnog
      } else if (subQuery.getLogicalOperator() == LogicalOperator.AND) {
        specificationQuery.and(specificationSubQuery);
      } else if (subQuery.getLogicalOperator() == LogicalOperator.OR) {
        specificationQuery.or(specificationSubQuery);
      }
    }

    return specificationQuery;
  }

  private static Specification<PhotographyDbo> createSubQuery(SubQuery subQuery) {
    Specification<PhotographyDbo> specificationSubQuery = Specification.where(null);

    for (Field field : subQuery.getFields()) {
      Specification<PhotographyDbo> specificationField = createField(field);
      if (field.getLogicalOperator() == null) {
        return specificationSubQuery.and(specificationField); // TODO: fix this
      } else if (field.getLogicalOperator() == LogicalOperator.AND) {
        specificationSubQuery.and(specificationField);
      } else if (field.getLogicalOperator() == LogicalOperator.OR) {
        specificationSubQuery.or(specificationField);
      }
    }

    return specificationSubQuery;
  }

  private static Specification<PhotographyDbo> createField(Field field) {
    return (root, query, criteriaBuilder) -> {

      String fieldName = mapFieldName(field.getFieldName());
      String searchParameter = field.getSearchParameter();

      if (field.getComparisonOperator() == ComparisonOperator.CONTAINS) {
        return criteriaBuilder.like(root.get(fieldName), "%" + searchParameter + "%");
      } else if (field.getComparisonOperator() == ComparisonOperator.EQUAL) {
        return criteriaBuilder.equal(root.get(fieldName), searchParameter);
      } else if (field.getComparisonOperator() == ComparisonOperator.NOT_EQUAL) {
        criteriaBuilder.notEqual(root.get(fieldName), searchParameter);
      }
      return null;
    };
  }


  private static String mapFieldName(FieldName fieldName) {
    if (fieldName == FieldName.NAME) {
      return "name";
    } else if (fieldName == FieldName.AUTHOR) {
      return "author";
    } else if (fieldName == FieldName.TAG) {
      return "tag";
    }

    throw new IllegalArgumentException("Non existing field name");
  }

}
