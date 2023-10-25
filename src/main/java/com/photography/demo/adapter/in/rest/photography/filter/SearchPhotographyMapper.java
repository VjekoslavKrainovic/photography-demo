package com.photography.demo.adapter.in.rest.photography.filter;

import com.photography.demo.domain.search.ComparisonOperator;
import com.photography.demo.domain.search.Field;
import com.photography.demo.domain.search.FieldName;
import com.photography.demo.domain.search.LogicalOperator;
import com.photography.demo.domain.search.SearchPhotography;
import com.photography.demo.domain.search.SubQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class SearchPhotographyMapper {

  private final SearchWordsRequest searchWordsRequest;

  public SearchPhotographyMapper(SearchWordsRequest searchWordsRequest) {
    this.searchWordsRequest = searchWordsRequest;
  }

  public SearchPhotography map(String filter, Pageable pageable) {

    if (filter == null || filter.isBlank()) {
      return new SearchPhotography(new ArrayList<>(), pageable);
    }

    List<String> filterKeywords = Arrays.stream(filter.split(" "))
        .toList();

    searchWordsRequest.validateFilter(filterKeywords);

    List<String> rawSubQueries = splitFilterKeywordsInRawSubqueries(filter);
    List<SubQuery> subQueries = mapRawSubQueriesToSubqueries(rawSubQueries);

    return new SearchPhotography(subQueries, pageable);
  }

  private List<String> splitFilterKeywordsInRawSubqueries(String filter) {

    List<String> rawSubqueries = new ArrayList<>();

    int indexStartOfSubquery = 0;
    int indexEndOfSubquery = 0;

    for (int i = 0; i < filter.length(); i++) {
      char character = filter.charAt(i);

      if (searchWordsRequest.isOpenBracket(String.valueOf(character)) || i == 0) {
        if (searchWordsRequest.isOpenBracket(String.valueOf(character)) && (i > 0)) {
          indexEndOfSubquery = i - 1; // we use minus one to not use closing bracket
          rawSubqueries.add(filter.substring(indexStartOfSubquery, indexEndOfSubquery));
        }
        if (i == 0) {
          indexStartOfSubquery = i;
        } else {
          indexStartOfSubquery = i + 1; // we use plus one to not use opening bracket
        }
      } else if (searchWordsRequest.isCloseBracket(String.valueOf(character))) {
        if ((i + 1) < filter.length()) {
          i += 5; // when there is closing bracket we add 5 so we can get Logical operator e.g. `) and` or ') or'
          // which is 5 MAX indexes after bracket
        }
        indexEndOfSubquery = i;
        rawSubqueries.add(filter.substring(indexStartOfSubquery, indexEndOfSubquery));
        indexStartOfSubquery = indexEndOfSubquery;
      } else if ((i + 1) == filter.length()) {
        indexEndOfSubquery = filter.length();
        rawSubqueries.add(filter.substring(indexStartOfSubquery, indexEndOfSubquery));
      }
    }

    return rawSubqueries;
  }

  private List<SubQuery> mapRawSubQueriesToSubqueries(List<String> subQueriesRaw) {

    List<SubQuery> subQueries = new ArrayList<>();

    for (String rawSubQuery : subQueriesRaw) {
      SubQuery subQuery = new SubQuery();

      FieldName fieldName = null;
      ComparisonOperator comparisonOperator = null;
      String searchParameter = null;
      LogicalOperator logicalOperator = null;

      String[] rawSubQuerySplited = rawSubQuery.split(" ");

      for (int i = 0; i < rawSubQuerySplited.length; i++) {

        if (searchWordsRequest.isFieldAuthor(rawSubQuerySplited[i])) {
          fieldName = FieldName.AUTHOR;
        } else if (searchWordsRequest.isFieldName(rawSubQuerySplited[i])) {
          fieldName = FieldName.NAME;
        } else if (searchWordsRequest.isFieldTag(rawSubQuerySplited[i])) {
          fieldName = FieldName.TAG;
        } else if (searchWordsRequest.isComparisonOperatorEquals(rawSubQuerySplited[i])) {
          comparisonOperator = ComparisonOperator.EQUAL;
        } else if (searchWordsRequest.isComparisonOperatorNotEquals(rawSubQuerySplited[i])) {
          comparisonOperator = ComparisonOperator.NOT_EQUAL;
        } else if (searchWordsRequest.isComparisonOperatorContains(rawSubQuerySplited[i])) {
          comparisonOperator = ComparisonOperator.CONTAINS;
        } else if (searchWordsRequest.isSearchParameter(rawSubQuerySplited[i])) {
          searchParameter = rawSubQuerySplited[i];
        } else if (searchWordsRequest.isLogicalOperatorOr(rawSubQuerySplited[i])) {
          logicalOperator = LogicalOperator.OR;

          if (rawSubQuerySplited.length == (i + 1)) {
            subQuery.addLogicalOperator(logicalOperator);
            subQuery.addField(new Field(fieldName, comparisonOperator, searchParameter, null));
            fieldName = null;
            comparisonOperator = null;
            searchParameter = null;
            logicalOperator = null;
          } else {
            subQuery.addField(new Field(fieldName, comparisonOperator, searchParameter, logicalOperator));
            fieldName = null;
            comparisonOperator = null;
            searchParameter = null;
            logicalOperator = null;
          }

        } else if (searchWordsRequest.isLogicalOperatorAnd(rawSubQuerySplited[i])) {

          logicalOperator = LogicalOperator.AND;

          if (rawSubQuerySplited.length == (i + 1)) {
            subQuery.addLogicalOperator(logicalOperator);
            subQuery.addField(new Field(fieldName, comparisonOperator, searchParameter, null));
            fieldName = null;
            comparisonOperator = null;
            searchParameter = null;
            logicalOperator = null;
          } else {
            subQuery.addField(new Field(fieldName, comparisonOperator, searchParameter, logicalOperator));
            fieldName = null;
            comparisonOperator = null;
            searchParameter = null;
            logicalOperator = null;
          }
        }
        
        if ((rawSubQuerySplited.length == (i + 1)) && !searchWordsRequest.isLogicalOperator(rawSubQuerySplited[i])) {
          subQuery.addField(new Field(fieldName, comparisonOperator, searchParameter, logicalOperator));
          fieldName = null;
          comparisonOperator = null;
          searchParameter = null;
          logicalOperator = null;
        }

      }
      subQueries.add(subQuery);
    }
    return subQueries;
  }

}
