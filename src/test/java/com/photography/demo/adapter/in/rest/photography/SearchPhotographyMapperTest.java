package com.photography.demo.adapter.in.rest.photography;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.photography.demo.adapter.in.rest.photography.filter.InvalidFilterKeywordException;
import com.photography.demo.adapter.in.rest.photography.filter.SearchPhotographyMapper;
import com.photography.demo.adapter.in.rest.photography.filter.SearchWordsRequest;
import com.photography.demo.domain.search.ComparisonOperator;
import com.photography.demo.domain.search.FieldName;
import com.photography.demo.domain.search.LogicalOperator;
import com.photography.demo.domain.search.SearchPhotography;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchPhotographyMapperTest {

  private SearchPhotographyMapper searchPhotographyMapper;

  @BeforeEach
  void setUp() {
    searchPhotographyMapper = new SearchPhotographyMapper(new SearchWordsRequest());
  }

  @Test
  void given_Valid_Keywords_Create_Search_Query_Case_One() {
    // prepare
    String filter = "tag eq ‘Nature’ and ( author eq ‘John’ or author eq ‘Mark’ )";

    // execute
    SearchPhotography searchPhotography = searchPhotographyMapper.map(filter, null);

    // verify
    assertThat(searchPhotography.getSubQueries()).hasSize(2);

    assertThat(searchPhotography.getSubQueries().get(0).getFields()).hasSize(1);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getFieldName()).isEqualTo(FieldName.TAG);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getSearchParameter()).isEqualTo("Nature");
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getLogicalOperator()).isNull();
    assertThat(searchPhotography.getSubQueries().get(0).getLogicalOperator()).isEqualTo(LogicalOperator.AND);

    assertThat(searchPhotography.getSubQueries().get(1).getLogicalOperator()).isNull();
    assertThat(searchPhotography.getSubQueries().get(1).getFields()).hasSize(2);
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(0).getFieldName()).isEqualTo(FieldName.AUTHOR);
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(0).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(0).getSearchParameter()).isEqualTo("John");
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(0).getLogicalOperator()).isEqualTo(LogicalOperator.OR);

    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(1).getFieldName()).isEqualTo(FieldName.AUTHOR);
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(1).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(1).getSearchParameter()).isEqualTo("Mark");
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(1).getLogicalOperator()).isNull();
  }

  @Test
  void given_Valid_Keywords_Create_Search_Query_Case_Two() {
    // prepare
    String filter = "( tag eq ‘Nature’ and author eq ‘John’ ) or author eq ‘Mark’";

    // execute
    SearchPhotography searchPhotography = searchPhotographyMapper.map(filter, null);

    // verify
    assertThat(searchPhotography.getSubQueries()).hasSize(2);

    assertThat(searchPhotography.getSubQueries().get(0).getFields()).hasSize(2);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getFieldName()).isEqualTo(FieldName.TAG);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getSearchParameter()).isEqualTo("Nature");
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getLogicalOperator()).isEqualTo(LogicalOperator.AND);

    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getFieldName()).isEqualTo(FieldName.AUTHOR);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getSearchParameter()).isEqualTo("John");
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getLogicalOperator()).isNull();

    assertThat(searchPhotography.getSubQueries().get(0).getLogicalOperator()).isEqualTo(LogicalOperator.OR);

    assertThat(searchPhotography.getSubQueries().get(1).getFields()).hasSize(1);
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(0).getFieldName()).isEqualTo(FieldName.AUTHOR);
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(0).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(0).getSearchParameter()).isEqualTo("Mark");
    assertThat(searchPhotography.getSubQueries().get(1).getFields().get(0).getLogicalOperator()).isNull();
    assertThat(searchPhotography.getSubQueries().get(1).getLogicalOperator()).isNull();
  }

  @Test
  void given_Valid_Keywords_Create_Search_Query_Case_Three() {
    // prepare
    String filter = "tag eq ‘Nature’ and author ne ‘John’";

    // execute
    SearchPhotography searchPhotography = searchPhotographyMapper.map(filter, null);

    // verify
    assertThat(searchPhotography.getSubQueries()).hasSize(1);

    assertThat(searchPhotography.getSubQueries().get(0).getFields()).hasSize(2);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getFieldName()).isEqualTo(FieldName.TAG);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getSearchParameter()).isEqualTo("Nature");
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getLogicalOperator()).isEqualTo(LogicalOperator.AND);

    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getFieldName()).isEqualTo(FieldName.AUTHOR);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getComparisonOperator()).isEqualTo(ComparisonOperator.NOT_EQUAL);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getSearchParameter()).isEqualTo("John");
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getLogicalOperator()).isNull();

    assertThat(searchPhotography.getSubQueries().get(0).getLogicalOperator()).isNull();
  }

  @Test
  void given_Valid_Keywords_Create_Search_Query_Case_Four() {
    // prepare
    String filter = "tag eq ‘Nature’ or author eq ‘John’";

    // execute
    SearchPhotography searchPhotography = searchPhotographyMapper.map(filter, null);

    // verify
    assertThat(searchPhotography.getSubQueries()).hasSize(1);

    assertThat(searchPhotography.getSubQueries().get(0).getFields()).hasSize(2);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getFieldName()).isEqualTo(FieldName.TAG);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getSearchParameter()).isEqualTo("Nature");
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(0).getLogicalOperator()).isEqualTo(LogicalOperator.OR);

    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getFieldName()).isEqualTo(FieldName.AUTHOR);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getComparisonOperator()).isEqualTo(ComparisonOperator.EQUAL);
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getSearchParameter()).isEqualTo("John");
    assertThat(searchPhotography.getSubQueries().get(0).getFields().get(1).getLogicalOperator()).isNull();

    assertThat(searchPhotography.getSubQueries().get(0).getLogicalOperator()).isNull();
  }

  @Test
  void given_Invalid_Keywords_Then_throw_Exception() {
    // prepare
    String filter = "( tag eqzyx ‘Nature’ and author eq ‘John’ ) or author eq ‘Mark’";

    // execute && verify
    assertThatThrownBy(() -> searchPhotographyMapper.map(filter, null))
        .isInstanceOf(InvalidFilterKeywordException.class)
        .hasMessage("Invalid filter keyword: eqzyx .");
  }

  @Test
  void given_Invalid_Keywords_Order_Then_throw_Exception_Case_One() {
    // prepare
    String filter = "( tag ‘Nature’ eq and author eq ‘John’ ) or author eq ‘Mark’";

    // execute && verify
    assertThatThrownBy(() -> searchPhotographyMapper.map(filter, null))
        .isInstanceOf(InvalidFilterKeywordException.class)
        .hasMessage("Invalid filter keyword: ‘Nature’ .");
  }

  @Test
  void given_Invalid_Keywords_Order_Then_throw_Exception_Case_Two() {
    // prepare
    String filter = "( tag and eq ‘Nature’ and author eq ‘John’ ) or author eq ‘Mark’";

    // execute && verify
    assertThatThrownBy(() -> searchPhotographyMapper.map(filter, null))
        .isInstanceOf(InvalidFilterKeywordException.class)
        .hasMessage("Invalid filter keyword: and .");
    ;
  }

  @Test
  void given_Invalid_Keywords_Order_Then_throw_Exception_Case_Three() {
    // prepare
    String filter = "( tag ) eq ‘Nature’ and author eq ‘John’ ) or author eq ‘Mark’";

    // execute && verify
    assertThatThrownBy(() -> searchPhotographyMapper.map(filter, null))
        .isInstanceOf(InvalidFilterKeywordException.class)
        .hasMessage("Invalid filter keyword: ) .");
    ;
  }

  @Test
  void given_Invalid_Keywords_Order_Then_throw_Exception_Case_Four() {
    // prepare
    String filter = "( tag eq ‘Nature’ and author eq ‘John’ ) or and author eq ‘Mark’";

    // execute && verify
    assertThatThrownBy(() -> searchPhotographyMapper.map(filter, null))
        .isInstanceOf(InvalidFilterKeywordException.class)
        .hasMessage("Invalid filter keyword: and .");
    ;
  }

}