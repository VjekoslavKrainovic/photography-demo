package com.photography.demo.domain.search;

public class Field {

  private FieldName fieldName;
  private ComparisonOperator comparisonOperator;
  private String searchParameter;
  private LogicalOperator logicalOperator;

  public Field(FieldName fieldName, ComparisonOperator comparisonOperator, String searchParameter,
      LogicalOperator logicalOperator) {
    this.fieldName = fieldName;
    this.comparisonOperator = comparisonOperator;
    this.searchParameter = searchParameter.replaceAll("[‘’]", "");
    this.logicalOperator = logicalOperator;
  }

  public FieldName getFieldName() {
    return fieldName;
  }

  public ComparisonOperator getComparisonOperator() {
    return comparisonOperator;
  }

  public String getSearchParameter() {
    return searchParameter;
  }

  public LogicalOperator getLogicalOperator() {
    return logicalOperator;
  }
}
