package com.photography.demo.adapter.out.persistance.repository;

import com.photography.demo.adapter.out.persistance.entity.PhotographyDbo;
import com.photography.demo.domain.search.ComparisonOperator;
import com.photography.demo.domain.search.Field;
import com.photography.demo.domain.search.FieldName;
import com.photography.demo.domain.search.LogicalOperator;
import com.photography.demo.domain.search.SearchPhotography;
import com.photography.demo.domain.search.SubQuery;
import jakarta.persistence.criteria.Path;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;


public class PhotographySpecification {

  private static final String FIELD_NAME = "name";
  private static final String FIELD_AUTHOR = "author";
  private static final String FIELD_TAGS = "tags";

  private PhotographySpecification() {
  }

  public static Specification<PhotographyDbo> createSearch(SearchPhotography searchPhotography) {

    Specification<PhotographyDbo> specificationQuery = Specification.where(null);

    if (searchPhotography.getSubQueries().isEmpty()) {
      return specificationQuery;
    }


    LogicalOperator lastSubqueryLogicalOperator = null;

    List<SubQuery> subQueries = searchPhotography.getSubQueries();
    for (int i = 0; i < subQueries.size(); i++) {
      SubQuery subQuery = subQueries.get(i);
      Specification<PhotographyDbo> specificationSubQuery = createSubQuery(subQuery);
      if (i == 0) {
        specificationQuery = specificationSubQuery;
        lastSubqueryLogicalOperator = subQuery.getLogicalOperator();
      } else if (lastSubqueryLogicalOperator == LogicalOperator.AND) {
        specificationQuery = specificationQuery.and(specificationSubQuery);
      } else if (lastSubqueryLogicalOperator == LogicalOperator.OR) {
        specificationQuery = specificationQuery.or(specificationSubQuery);
      }

    }

    return specificationQuery.and(distinct());
  }

  public static Specification<PhotographyDbo> createSubQuery(SubQuery subQuery) {
    Specification<PhotographyDbo> specificationSubQuery = null;

    LogicalOperator lastFieldLogicalOperator = null;

    List<Field> fields = subQuery.getFields();

    for (int i = 0; i < fields.size(); i++) {

      Field field = fields.get(i);
      Specification<PhotographyDbo> specificationField = createField(field);

      if (i == 0) {
        specificationSubQuery = specificationField;
        lastFieldLogicalOperator = field.getLogicalOperator();
      } else if (lastFieldLogicalOperator == LogicalOperator.AND) {
        specificationSubQuery = specificationSubQuery.and(specificationField);
      } else if (lastFieldLogicalOperator == LogicalOperator.OR) {
        specificationSubQuery = specificationSubQuery.or(specificationField);
      }

    }

    return specificationSubQuery;
  }

  public static Specification<PhotographyDbo> createField(Field field) {
    return (root, query, criteriaBuilder) -> {

      String fieldName = mapFieldName(field.getFieldName());
      String searchParameter = field.getSearchParameter();

      Path<String> fieldNamePath;

      if (field.getFieldName() == FieldName.TAG) {
        fieldNamePath = root.get(fieldName).get(FIELD_NAME);
      } else {
        fieldNamePath = root.get(fieldName);
      }

      if (field.getComparisonOperator() == ComparisonOperator.CONTAINS) {
        return criteriaBuilder.like(fieldNamePath, "%" + searchParameter + "%");
      } else if (field.getComparisonOperator() == ComparisonOperator.EQUAL) {
        return criteriaBuilder.equal(fieldNamePath, searchParameter);
      } else if (field.getComparisonOperator() == ComparisonOperator.NOT_EQUAL) {
        criteriaBuilder.notEqual(fieldNamePath, searchParameter);
      }
      return null;
    };
  }

  private static String mapFieldName(FieldName fieldName) {
    if (fieldName == FieldName.NAME) {
      return FIELD_NAME;
    } else if (fieldName == FieldName.AUTHOR) {
      return FIELD_AUTHOR;
    } else if (fieldName == FieldName.TAG) {
      return FIELD_TAGS;
    }

    throw new IllegalArgumentException("Non existing field name");
  }

  private static Specification<PhotographyDbo> distinct(){
     return (root, query, criteriaBuilder) -> {
       query.distinct(true);
       return null;
     };
  }

}
