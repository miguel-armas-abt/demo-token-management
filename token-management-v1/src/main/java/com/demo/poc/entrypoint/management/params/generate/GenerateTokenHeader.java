package com.demo.poc.entrypoint.management.params.generate;

import com.demo.poc.commons.core.validations.headers.DefaultHeaders;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GenerateTokenHeader extends DefaultHeaders {

  @NotEmpty
  private String platform;
}
