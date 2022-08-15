package byuntil.backend.research.service;

import byuntil.backend.common.exception.ExistException;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.response.field.FieldAdminResponseDto;
import byuntil.backend.research.dto.response.field.FieldListAdminResponseDto;
import byuntil.backend.research.dto.response.field.FieldListResponseDto;
import byuntil.backend.research.dto.response.field.FieldResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FieldService {

    private final FieldRepository fieldRepository;

    public Optional<Field> findByName(String name){
        return fieldRepository.findByName(name);
    }

    @Transactional
    public Long save(final FieldDto fieldDto) {
        Field field = fieldDto.toEntity();
        return fieldRepository.save(field).getId();
    }

    @Transactional
    public Long update(final FieldDto fieldDto, Long id) {
        Field origin = fieldRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("찾는 연구분야가 없습니다. id = " + id));
        origin.update(fieldDto);

        return origin.getId();

    }

    //아무랑도 연관관계가 없을때만 지운다.. 만약에 list를 가지고 있으면 그거 모두 옮겨주거나?해서 list를 비워준다음에 delete시켜야함
    @Transactional
    public void deleteById(final Long id) {
        Field field = fieldRepository.findById(id).get();
        if (field.getProjectList().size() == 0 && field.getThesisList().size() == 0) {
            fieldRepository.deleteById(id);
        } else {
            throw new ExistException("project, demo, thesis가 모두 비워져야 합니다.");
        }

    }

    public FieldDto findById(final Long id) {
        Field field = fieldRepository.findById(id).get();
        return FieldDto.builder().description(field.getDescription()).fieldName(field.getName()).build();
    }

    public List<byuntil.backend.research.dto.response.field.FieldDto> findAllWithName() {
        List<Field> fields = fieldRepository.findAll();
        List<byuntil.backend.research.dto.response.field.FieldDto> fieldDtoList = new ArrayList<>();
        for (Field field: fields) {
            fieldDtoList.add(byuntil.backend.research.dto.response.field.FieldDto.builder().fieldName(field.getName()).build());
        }
        return fieldDtoList;
    }
    public FieldListResponseDto findAll(){
        List<Field> fields = fieldRepository.findAll();
        List<FieldResponseDto> dtoList = new ArrayList<>();
        for (Field field: fields) {
            dtoList.add(FieldResponseDto.builder().fieldName(field.getName()).description(field.getDescription())
                    .build());
        }
        return FieldListResponseDto.builder().fields(dtoList).build();
    }
    public FieldListAdminResponseDto findAllAdmin(){
        List<Field> fields = fieldRepository.findAll();
        List<FieldAdminResponseDto> dtoList = new ArrayList<>();
        for (Field field: fields) {
            dtoList.add(FieldAdminResponseDto.builder().fieldName(field.getName()).description(field.getDescription())
                    .id(field.getId()).build());
        }
        return FieldListAdminResponseDto.builder().fields(dtoList).build();
    }

}
