package com.denik.vy;

import com.denik.vy.models.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {
    final static String URL = "http://94.198.50.185:7081/api/users";
    // restTemplate с помощью него отправляем запросы (POST, GET...)
    final static RestTemplate restTemplate = new RestTemplate();
    // заголовок запроса для отправки его с restTemplate
    private static HttpHeaders headers = new HttpHeaders();

    public static void main(String[] args) {

        StringBuilder string = new StringBuilder();

        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("cookie", response.getHeaders().get("set-cookie").get(0));

        StringBuffer answer = new StringBuffer();
        answer.append(post(new User(3L, "James", "Brown", (byte) 40)))
                .append(put(new User(3L, "Thomas", "Shelby", (byte) 40)))
                .append(delete(3L));

        System.out.println(answer);
    }
    private static String post(User user) {

        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);

        return responseEntity.getBody();
    }

    private static String put(User user) {

        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, httpEntity, String.class);

        return responseEntity.getBody();
    }

    private static String delete(long id) {

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(String.format(URL + "/%d", id), HttpMethod.DELETE, httpEntity, String.class);

        return responseEntity.getBody();
    }
}