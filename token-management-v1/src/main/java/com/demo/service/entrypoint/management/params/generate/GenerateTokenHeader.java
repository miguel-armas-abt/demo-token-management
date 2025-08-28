package com.demo.service.entrypoint.management.params.generate;

import com.demo.commons.validations.headers.DefaultHeaders;
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
