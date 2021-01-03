package test.com.wuxp.security.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

public class UpCaseJsonTest {

    public static void main(String[] args) throws Exception{
        ObjectMapper objectMapper=new ObjectMapper();
        ExampleDTO value = new ExampleDTO();
        String json = objectMapper.writeValueAsString(value);
        System.out.print(json);
    }

    @Data
    @JsonNaming(value = PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    static class ExampleDTO{
        private Long id;

        private String name;
    }
}
