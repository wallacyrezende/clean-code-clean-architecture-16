package cleancodecleanarchitecture16.course.aula1.mapper;

import static org.springframework.util.ObjectUtils.isEmpty;

public class Mapper {
    protected static <T> boolean isNullOrEmpty(T object){
        return isEmpty(object);
    }
}
