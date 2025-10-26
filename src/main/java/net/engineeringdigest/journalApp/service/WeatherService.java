package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    String API;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        String redisKey = "weather_of_" + city;
        WeatherResponse weatherResponse = redisService.get(redisKey, WeatherResponse.class);

        if(weatherResponse != null){
            return weatherResponse;
        }else {
            String finalApi = API.replace("API_KEY",apiKey).replace("CITY",city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set(redisKey, body, 300L);
            }
            return body;
        }
    }
}
