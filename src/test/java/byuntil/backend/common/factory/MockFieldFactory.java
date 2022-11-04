package byuntil.backend.common.factory;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.dto.request.FieldDto;

import java.util.ArrayList;
import java.util.List;

public class MockFieldFactory {

    public static Field createMockField(int count) {
        return Field.builder().name("연구분야" + count).description("설명" + count).build();
    }

    public static List<Field> createMockFields(int fieldNum) {
        List<Field> list = new ArrayList<>();
        for (int i = 0; i < fieldNum; i++) {
            Field mockField = createMockField(i);
            list.add(mockField);
        }
        return list;
    }

}
