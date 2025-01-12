package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> memberListV1() {
        return memberService.findAll();
    }

    @GetMapping("/api/v2/members")
    public MemberDto.Result memberListV2() {
        List<Member> memberList = memberService.findAll();
        List<MemberDto.MemberListDto> collect =
        memberList.stream()
                .map(m -> new MemberDto.MemberListDto(m.getName()))
                .collect(Collectors.toList());
        return new MemberDto.Result(collect.size(), collect);
    }

    @PostMapping("/api/v1/members")
    // @RequestBody : json으로 온 body -> member 로 변환
    public MemberDto.CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new MemberDto.CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    // @RequestBody : json으로 온 body -> member 로 변환
    public MemberDto.CreateMemberResponse saveMemberV2(@RequestBody @Valid MemberDto.CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new MemberDto.CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public MemberDto.UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid MemberDto.UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new MemberDto.UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

}