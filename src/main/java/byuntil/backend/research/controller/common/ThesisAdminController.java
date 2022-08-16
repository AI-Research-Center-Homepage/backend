package byuntil.backend.research.controller.common;

import byuntil.backend.member.dto.response.MemberResponseDto;
import byuntil.backend.member.service.MemberService;
import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.request.ThesisDto;
import byuntil.backend.research.dto.response.thesis.findAllInfoDto;
import byuntil.backend.research.dto.response.field.MemberFieldDto;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ThesisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/thesis")
public class ThesisAdminController {
    private final FieldService fieldService;
    private final ThesisService thesisService;
    private final MemberService memberService;

    @GetMapping("/new")
    public ResponseEntity readMemberField(){
        List<MemberResponseDto> members = memberService.findAllMember();
        List<byuntil.backend.research.dto.response.field.FieldDto> fieldDtoList = fieldService.findAllWithName();
        MemberFieldDto memberFieldDto = MemberFieldDto.builder().members(members).fields(fieldDtoList).build();

        return ResponseEntity.status(HttpStatus.OK).body(memberFieldDto);
    }
    @PostMapping("/new")
    public ResponseEntity create(@RequestBody ThesisDto thesisDto){
        //TODO : 지워야하는 코드
        if(!fieldService.findByName("연구분야1").isPresent()) fieldService.save(FieldDto.builder().description("설명").fieldName("연구분야1").build());

        thesisService.save(thesisDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PutMapping
    public ResponseEntity update(@RequestParam("thesisId") Long id, @RequestBody ThesisDto thesisDto){
        thesisDto.setId(id);
        thesisService.update(thesisDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("thesisId") Long id){
        thesisService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    @GetMapping
    public findAllInfoDto readAll(){
        return thesisService.findAllAdmin();
    }
    @GetMapping("/{thesisId}")
    public ResponseEntity readById(@PathVariable("thesisId") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(thesisService.findById(id));
    }




}
