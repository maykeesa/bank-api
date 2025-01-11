package br.com.bank.api.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

public class ResponseDto {

    public enum Post{;

        @Data
        @AllArgsConstructor
        public static class Response{
            private int status;
            private Object body;

        }

    }


}
