package com.photography.demo.adapter.in.rest.photography.filter;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SearchWordsRequest {

  private static final String FIELD_NAME = "name";
  private static final String FIELD_TAG = "tag";
  private static final String FIELD_AUTHOR = "author";
  private static final String COMPARISON_OPERATOR_EQUALS = "eq";
  private static final String COMPARISON_OPERATOR_NOT_EQUALS = "ne";
  private static final String COMPARISON_OPERATOR_CONTAINS = "contains";
  private static final String LOGICAL_OPERATOR_AND = "and";
  private static final String LOGICAL_OPERATOR_OR = "or";
  private static final String OPEN_BRACKET = "(";
  private static final String CLOSE_BRACKET = ")";
  private static final String START_OF_SEARCH_PARAMETER_MARK = "‘";
  private static final String END_OF_SEARCH_PARAMETER_MARK = "’";

  private final List<String> validKeywords = new ArrayList<>();


  public SearchWordsRequest() {
    validKeywords.add(FIELD_NAME);
    validKeywords.add(FIELD_TAG);
    validKeywords.add(FIELD_AUTHOR);
    validKeywords.add(COMPARISON_OPERATOR_EQUALS);
    validKeywords.add(COMPARISON_OPERATOR_NOT_EQUALS);
    validKeywords.add(COMPARISON_OPERATOR_CONTAINS);
    validKeywords.add(LOGICAL_OPERATOR_AND);
    validKeywords.add(LOGICAL_OPERATOR_OR);
    validKeywords.add(OPEN_BRACKET);
    validKeywords.add(CLOSE_BRACKET);
  }

  public boolean isValidFilter(List<String> keywords) {

    String lastKeyword = null;

    for (String keyword : keywords) {
      if (!isSearchParameter(keyword) && !validKeywords.contains(keyword)) {
        log.debug("Keyword: {} is invalid.", keyword);
        return false;
      }

      if (!isValidOrder(lastKeyword, keyword)) {
        return false;
      }

      lastKeyword = keyword;
    }

    return true;
  }

  public boolean isSearchParameter(String keyword) {
    return keyword.startsWith(START_OF_SEARCH_PARAMETER_MARK) && keyword.endsWith(END_OF_SEARCH_PARAMETER_MARK);
  }

  private boolean isValidOrder(String keywordBefore, String currentKeyword) {

    if (keywordBefore == null && (currentKeyword != null && isField(currentKeyword))) {
      return true;
    } else if (keywordBefore == null && isBracket(currentKeyword)) {
      return true;
    } else if (isField(keywordBefore) && isComparisonOperator(currentKeyword)) {
      return true;
    } else if (isComparisonOperator(keywordBefore) && isSearchParameter(currentKeyword)) {
      return true;
    } else if (isSearchParameter(keywordBefore) && isLogicalOperator(currentKeyword)) {
      return true;
    } else if (isBracket(keywordBefore) && isField(currentKeyword)) {
      return true;
    } else if (isLogicalOperator(keywordBefore) && isField(currentKeyword)) {
      return true;
    } else if (isSearchParameter(keywordBefore) && isBracket(currentKeyword)) {
      return true;
    } else if (isBracket(keywordBefore) && isLogicalOperator(currentKeyword)) {
      return true;
    } else {
      return isLogicalOperator(keywordBefore) && isBracket(currentKeyword);
    }
  }

  public boolean isFieldName(String keyword) {
    return keyword.equals(FIELD_NAME);
  }

  public boolean isFieldTag(String keyword) {
    return keyword.equals(FIELD_TAG);
  }

  public boolean isFieldAuthor(String keyword) {
    return keyword.equals(FIELD_AUTHOR);
  }

  private boolean isField(String keyword) {
    return isFieldName(keyword) || isFieldTag(keyword) || isFieldAuthor(keyword);
  }

  public boolean isComparisonOperatorEquals(String keyword) {
    return keyword.equals(COMPARISON_OPERATOR_EQUALS);
  }

  public boolean isComparisonOperatorNotEquals(String keyword) {
    return keyword.equals(COMPARISON_OPERATOR_NOT_EQUALS);
  }

  public boolean isComparisonOperatorContains(String keyword) {
    return keyword.equals(COMPARISON_OPERATOR_CONTAINS);
  }

  private boolean isComparisonOperator(String keyword) {
    return isComparisonOperatorEquals(keyword) || isComparisonOperatorNotEquals(keyword)
        || isComparisonOperatorContains(keyword);
  }

  public boolean isLogicalOperatorAnd(String keyword) {
    return keyword.equals(LOGICAL_OPERATOR_AND);
  }

  public boolean isLogicalOperatorOr(String keyword) {
    return keyword.equals(LOGICAL_OPERATOR_OR);
  }

  public boolean isLogicalOperator(String keyword) {
    return isLogicalOperatorAnd(keyword) || isLogicalOperatorOr(keyword);
  }

  public boolean isOpenBracket(String keyword) {
    return keyword.equals(OPEN_BRACKET);
  }

  public boolean isCloseBracket(String keyword) {
    return keyword.equals(CLOSE_BRACKET);
  }

  private boolean isBracket(String keyword) {
    return isOpenBracket(keyword) || isCloseBracket(keyword);
  }

}
