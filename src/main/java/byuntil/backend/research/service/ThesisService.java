package byuntil.backend.research.service;

import byuntil.backend.common.exception.research.NullFieldException;
import byuntil.backend.member_thesis.service.Member_ThesisService;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.domain.repository.ThesisRepository;
import byuntil.backend.research.dto.request.MemberDto;
import byuntil.backend.research.dto.request.ThesisDto;
import byuntil.backend.research.dto.response.AllThesisResponseDto;
import byuntil.backend.research.dto.response.OneThesisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThesisService {
    private final FieldRepository fieldRepository;
    private final ThesisRepository thesisRepository;
    private final Member_ThesisService member_thesisService;

    @Transactional
    public Long save(final ThesisDto thesisDto) {
        //thesisDto.name이 null이면 예외가 터져야함
        Field field = fieldRepository.findByName(thesisDto.getFieldName()).orElseThrow(() -> new NullFieldException());

        Thesis thesis = thesisDto.toEntity();


        Optional.ofNullable(thesisDto.getMembers()).ifPresentOrElse(
                members -> {
                    for (MemberDto memberDto: members) {
                        member_thesisService.create(memberDto.getId(), thesis);
                    }
                }
                ,
                () -> {}

        );
        field.addThesis(thesis);
        return thesisRepository.save(thesis).getId();
    }

    @Transactional
    public void update(final ThesisDto thesisDto) {
        Thesis thesis = thesisRepository.findById(thesisDto.getId()).orElseThrow(()
                -> new IllegalArgumentException("해당 논문이 없습니다. id = " + thesisDto.getId()));
        thesis.update(thesisDto);
        //여기서 members에 대한 정보도 update돼야함. 매번 새로 설정
        member_thesisService.deleteByThesisId(thesis.getId());
        for (MemberDto dto : thesisDto.getMembers()) {
            member_thesisService.create(dto.getId(), thesis) ;
        }

        if (!thesis.getField().getName().equals(thesisDto.getFieldName())) {
            //내용만 변경되는건 안됨 field는 field혼자만 변경되어야함
            Field field = fieldRepository.findByName(thesisDto.getFieldName()).get();
            field.addThesis(thesis);
        }
    }

    @Transactional
    public void delete(final Long id) {
        Thesis thesis = thesisRepository.findById(id).get();
        thesis.deleteMemberThesis();
        member_thesisService.deleteByThesisId(id);
        thesisRepository.deleteById(id);
    }

    public ThesisDto findById(final Long id) {
        return thesisRepository.findById(id).get().toDto();
    }

    public List<AllThesisResponseDto> findAll() {
        List<String> fieldNames = new ArrayList<>();
        List<AllThesisResponseDto> thesisDtoList = new ArrayList<>();
        for (Field field: fieldRepository.findAll()) {
            fieldNames.add(field.getName());
        }
        for (String fieldName: fieldNames) {
            List<ThesisDto> thesisDtoListByName = findAllByFieldName(fieldName);
            List<OneThesisDto> oneThesisDtoList = new ArrayList<>();
            for (ThesisDto thesisDto : thesisDtoListByName) {
                OneThesisDto one = OneThesisDto.builder().journal(thesisDto.getJournal()).id(thesisDto.getId()).publishDate(thesisDto.getPublishDate())
                                .title(thesisDto.getTitle()).build();
                oneThesisDtoList.add(one);
            }
            thesisDtoList.add(AllThesisResponseDto.builder().fieldName(fieldName).theses(oneThesisDtoList).build());
        }

        return thesisDtoList;
    }

    public List<ThesisDto> findAllByFieldName(final String name) {
        List<ThesisDto> thesisDtoList = new ArrayList<>();
        for (Thesis thesis:thesisRepository.findAllByFieldName(name)) {
            thesisDtoList.add(thesis.toDto());

        }
        return thesisDtoList;
    }
}
