package com.photography.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.photography.demo.application.exception.InvalidSizeNumberException;
import com.photography.demo.domain.photography.Size;
import org.junit.jupiter.api.Test;

class SizeTest {

  @Test
  void given_Valid_Size_Then_Create() {

    // prepare and execute
    Size size = Size.of(1.0);

    // verify
    assertThat(size.size()).isEqualTo(1.0);
  }

  @Test
  void given_Invalid_Size_Then_ThrowException() {

    // prepare && execute && verify
    assertThatThrownBy(() -> Size.of(0.0))
        .isInstanceOf(InvalidSizeNumberException.class);

  }

}