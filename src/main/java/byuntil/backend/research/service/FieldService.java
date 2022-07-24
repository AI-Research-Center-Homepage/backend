package byuntil.backend.research.service;

import byuntil.backend.common.exception.ExistException;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.dto.request.FieldDto;
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
    public Long update(final FieldDto fieldDto) {
        Field origin = fieldRepository.findById(fieldDto.getId()).orElseThrow(()
                -> new IllegalArgumentException("찾는 연구분야가 없습니다. id = " + fieldDto.getId()));
        origin.update(fieldDto);

        return fieldDto.getId();
    }

    //아무랑도 연관관계가 없을때만 지운다.. 만약에 list를 가지고 있으면 그거 모두 옮겨주거나?해서 list를 비워준다음에 delete시켜야함
    @Transactional
    public void deleteById(final Long id) {
        Field field = fieldRepository.findById(id).get();
        if (field.getProjectList() == null && field.getThesisList() == null) {
            fieldRepository.deleteById(id);
        } else {
            throw new ExistException("project, demo, thesis가 모두 비워져야 합니다.");
        }

    }

    public Optional<Field> findById(final Long id) {
        return fieldRepository.findById(id);
    }

    public List<byuntil.backend.research.dto.response.FieldDto> findAll() {
        List<Field> fields = fieldRepository.findAll();
        List<byuntil.backend.research.dto.response.FieldDto> fieldDtoList = new ArrayList<>();
        for (Field field: fields) {
            fieldDtoList.add(byuntil.backend.research.dto.response.FieldDto.builder().fieldName(field.getName()).build());
        }
        return fieldDtoList;
    }
}
