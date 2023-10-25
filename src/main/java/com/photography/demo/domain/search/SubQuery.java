package com.photography.demo.domain.search;

import java.util.ArrayList;
import java.util.List;

public class SubQuery {

  private List<Field> fields = new ArrayList<>();
  private LogicalOperator logicalOperator;

  public void addField(Field field){
    this.fields.add(field);
  }

  public void addLogicalOperator(LogicalOperator logicalOperator){
    this.logicalOperator = logicalOperator;
  }

  public List<Field> getFields() {
    return fields;
  }

  public LogicalOperator getLogicalOperator() {
    return logicalOperator;
  }
}
