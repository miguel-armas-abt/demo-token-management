package com.demo.service.entrypoint.management.params.clean;

import java.util.Map;
import java.util.TreeMap;

import com.demo.commons.tracing.enums.ForwardedParam;
import com.demo.commons.tracing.enums.TraceParam;
import com.demo.commons.validations.ParamMapper;

import org.springframework.stereotype.Component;

@Component
public class CleanTokenHeaderMapper implements ParamMapper<CleanTokenHeader> {

  private static final String PLATFORM_KEY = "platform";

  @Override
  public Map.Entry<CleanTokenHeader, Map<String, String>> map(Map<String, String> params) {
    CleanTokenHeader headers = new CleanTokenHeader();
    headers.setTraceParent(params.get(TraceParam.TRACE_PARENT.getKey()));
    headers.setChannelId(params.get(ForwardedParam.CHANNEL_ID.getKey()));
    headers.setPlatform(params.get(PLATFORM_KEY));

    Map<String, String> headersMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    headersMap.put(TraceParam.TRACE_PARENT.getKey(), headers.getTraceParent());
    headersMap.put(ForwardedParam.CHANNEL_ID.getKey(), headers.getChannelId());
    headersMap.put(PLATFORM_KEY, headers.getPlatform());

    return Map.entry(headers, headersMap);
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return CleanTokenHeader.class.isAssignableFrom(paramClass);
  }
}
