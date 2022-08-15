package byuntil.backend.research.controller.common;

import byuntil.backend.member.dto.response.MemberResponseDto;
import byuntil.backend.member.service.MemberService;
import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.request.ThesisDto;
import byuntil.backend.research.dto.response.thesis.AllThesisResponseDto;
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
@RequestMapping("/api/v1/admin/thesis")
public class ThesisAdminController {
    private final FieldService fieldService;
    private final ThesisService thesisService;
    private final MemberService memberService;

    @GetMapping("/new")
    public ResponseEntity readMemberField(){
        List<MemberResponseDto> members = memberService.findAllMember();
        List<byuntil.backend.research.dto.response.field.FieldDto> fieldDtoList = fieldService.findAllWithName();
        MemberFieldDto memberFieldDto = MemberFieldDto.builder().memberDtoList(members).fieldDtoList(fieldDtoList).build();

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
    public ResponseEntity update(@RequestParam Long id, @RequestBody ThesisDto thesisDto){
        thesisDto.setId(id);
        thesisService.update(thesisDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @DeleteMapping

    public ResponseEntity delete(@RequestParam Long id){
        thesisService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    @GetMapping
    public List<AllThesisResponseDto> readAll(){
        return thesisService.findAllAdmin();
    }
    @GetMapping("/{id}")
    public ResponseEntity readById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(thesisService.findById(id));
    }




}
