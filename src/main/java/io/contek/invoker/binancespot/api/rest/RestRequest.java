package io.contek.invoker.binancespot.api.rest;

import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.http.AnyHttpException;
import io.contek.invoker.commons.rest.*;
import io.contek.invoker.security.ICredential;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;

import static io.contek.invoker.commons.rest.RestMediaType.FORM;

@ThreadSafe
public abstract class RestRequest<R> extends BaseRestRequest<R> {

  private static final String X_MBX_API_KEY = "X-MBX-APIKEY";
  private static final String SIGNATURE = "signature";

  private final RestContext context;

  protected RestRequest(IActor actor, RestContext context) {
    super(actor);
    this.context = context;
  }

  protected abstract RestMethod getMethod();

  protected abstract String getEndpointPath();

  protected abstract RestParams getParams();

  @Override
  protected RestCall createCall(ICredential credential) { // modified (removed final) to allow PostSpotListener to remove the signature
    RestMethod method = getMethod();
    switch (method) {
      case GET:
      case DELETE:
        return RestCall.newBuilder()
            .setUrl(buildUrlWithParams(credential))
            .setMethod(method)
            .setHeaders(buildHeaders(credential))
            .build();
      case POST:
      case PUT:
        RestMediaBody body = FORM.createBody(getParams());
        return RestCall.newBuilder()
            .setUrl(buildUrlWithoutParams(body.getStringValue(), credential))
            .setMethod(method)
            .setHeaders(buildHeaders(credential))
            .setBody(body)
            .build();
      default:
        throw new IllegalStateException(getMethod().name());
    }
  }

  @Override
  protected final void checkResult(R result, RestResponse response) throws AnyHttpException {}

  private Map<String, String> buildHeaders(ICredential credential) {
    if (credential.isAnonymous()) {
      return Map.of();
    }
    return Map.of(X_MBX_API_KEY, credential.getApiKeyId());
  }

  private String buildUrlWithParams(ICredential credential) {
    String url = context.getBaseUrl() + getEndpointPath();
    String params = buildUrlParamsString(credential);
    if (!params.isEmpty()) {
      url += "?" + params;
    }
    return url;
  }

  private String buildUrlParamsString(ICredential credential) {
    RestParams params = getParams();
    if (credential.isAnonymous()) {
      return params.getQueryString();
    }
    return addSignature(params, "", credential).getQueryString();
  }

  private String buildUrlWithoutParams(String bodyString, ICredential credential) {
    String url = context.getBaseUrl() + getEndpointPath();
    if (credential.isAnonymous()) {
      return url;
    }
    return url + "?" + addSignature(RestParams.empty(), bodyString, credential).getQueryString();
  }

  private static RestParams addSignature(
      RestParams params, String bodyString, ICredential credential) {
    String payload = params.getQueryString() + bodyString;
    String signature = credential.sign(payload);
    return RestParams.newBuilder().addAll(params.getValues()).add(SIGNATURE, signature).build();
  }
}
