package com.demo.poc.entrypoint.management.params.generate;

import java.util.Map;

import com.demo.poc.commons.core.validations.ParamMapper;

import org.springframework.stereotype.Component;

@Component
public class GenerateTokenHeaderMapper implements ParamMapper {

  @Override
  public Object map(Map<String, String> params) {
    GenerateTokenHeader headers = new GenerateTokenHeader();
    headers.setTraceId(params.get("trace-id"));
    headers.setChannelId(params.get("channel-id"));
    headers.setPlatform(params.get("platform"));
    return headers;
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return GenerateTokenHeader.class.isAssignableFrom(paramClass);
  }
}
