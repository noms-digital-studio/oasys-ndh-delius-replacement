package uk.gov.justice.digital.ndh.service;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.cache.LoadingCache;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.http.HttpStatus;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Builder
public class NomisClient {

    public static final String OAUTH_TOKEN = "oauthToken";
    private final String baseUrl;
    private final LoadingCache<String, Optional<String>> oauthTokenProvider;

    public HttpResponse<String> doGet(String relativeUrl, Map<String, Object> params) throws UnirestException, ExecutionException {

        val token = oauthTokenProvider.get(OAUTH_TOKEN);

        final String url = baseUrl + relativeUrl;

        log.info("Doing GET {} with params {}", relativeUrl, params);
        final HttpResponse<String> response = Unirest.get(url)
                .header("Authorization", "Bearer " + token.orElse(""))
                .queryString(params)
                .asString();

        if (isUnauthorised(response)) {
            log.warn("Response unauthorised...");
            oauthTokenProvider.invalidate(OAUTH_TOKEN);
        }

        return response;

    }

    public boolean isUnauthorised(HttpResponse<String> response) {
        return response.getStatus() == HttpStatus.SC_UNAUTHORIZED || response.getStatus() == HttpStatus.SC_FORBIDDEN;
    }

    public Optional<HttpResponse<String>> doGetWithRetry(String relativeUrl, Map<String, Object> params) throws ExecutionException, RetryException {

        Retryer<HttpResponse<String>> retryer = RetryerBuilder.<HttpResponse<String>>newBuilder()
                .retryIfResult(this::isUnauthorised)
                .withWaitStrategy(WaitStrategies.noWait())
                .withStopStrategy(StopStrategies.stopAfterAttempt(2))
                .build();

        try {
            return Optional.ofNullable(retryer.call(() -> doGet(relativeUrl, params)));
        } catch (RetryException | ExecutionException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Optional<HttpResponse<String>> doGetWithRetry(String relativeUrl) throws UnirestException, ExecutionException, RetryException {
        return doGetWithRetry(relativeUrl, null);
    }
}
