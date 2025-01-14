package br.com.bank.api.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ResponseDto {

    public enum Body{;

        @Data
        @AllArgsConstructor
        public static class Response{
            private int status;
            private Object body;
        }

        @Data
        @AllArgsConstructor
        public static class ResponseError{
            private int status;
            private Object error;
            private Object cause;
        }

    }


}
