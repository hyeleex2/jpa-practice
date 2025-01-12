package jpabook.jpashop.dto;

import lombok.*;

public class MemberDto {

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
