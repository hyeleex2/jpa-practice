package jpabook.jpashop.dto;

import jpabook.jpashop.domain.Address;
import lombok.*;

public class MemberDto {

    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private int count;
        private T data;
    }

    @AllArgsConstructor
    @Data
    public static class MemberListDto {
//        private Long id;
        private String name;
//        private Address address;
    }

    @Data
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateMemberRequest {
        private String name;
    }


    @Data
    @Getter
    @AllArgsConstructor
    public static class CreateMemberResponse {
        private Long id;
    }

    @Data
    public static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    public static class UpdateMemberResponse {
        private Long id;
        private String name;
    }


}
