package com.thoughtworks.kanjuice.restService.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.function.Predicate;

@Configuration
public class RestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestConfig.class);

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        setCustomObjectMapper(restTemplate);
        setCustomHttpRequestFactory(restTemplate);
        return restTemplate;
    }

    private void setCustomObjectMapper(RestTemplate restTemplate) {
        Optional<HttpMessageConverter<?>> jacksonConverter = getHttpJacksonMessageConverter(restTemplate);
        if (jacksonConverter.isPresent()) {
            ((MappingJackson2HttpMessageConverter) jacksonConverter.get()).setObjectMapper(new JsonMapper());
        }
    }

    private void setCustomHttpRequestFactory(RestTemplate restTemplate) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setSSLContext(createSSLContextWithDisabledCertificateCheck())
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);
    }

    private SSLContext createSSLContextWithDisabledCertificateCheck() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            javax.net.ssl.TrustManager[] trustManagers = {new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            }};

            sslContext.init(null, trustManagers, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            LOGGER.error("Couldn't create SSL context to disable certificate check", e);
            return null;
        }
    }

    private Optional<HttpMessageConverter<?>> getHttpJacksonMessageConverter(RestTemplate restTemplate) {
        return restTemplate.getMessageConverters().stream()
                .filter(isMappingJacksonMessageConverter())
                .findFirst();
    }

    private Predicate<HttpMessageConverter<?>> isMappingJacksonMessageConverter() {
        return converter -> converter instanceof MappingJackson2HttpMessageConverter;
    }
}