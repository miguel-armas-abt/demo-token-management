package com.demo.poc.entrypoint.management.params.generate;

import java.util.Map;

import com.demo.poc.commons.core.tracing.enums.ForwardedParam;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
import com.demo.poc.commons.core.validations.ParamMapper;

import org.springframework.stereotype.Component;

@Component
public class GenerateTokenHeaderMapper implements ParamMapper {

  @Override
  public Object map(Map<String, String> params) {
    GenerateTokenHeader headers = new GenerateTokenHeader();
    headers.setTraceParent(params.get(TraceParam.TRACE_PARENT.getKey().toLowerCase()));
    headers.setChannelId(params.get(ForwardedParam.CHANNEL_ID.getKey().toLowerCase()));
    headers.setPlatform(params.get("platform"));
    return headers;
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return GenerateTokenHeader.class.isAssignableFrom(paramClass);
  }
}
