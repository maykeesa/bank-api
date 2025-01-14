package br.com.bank.api.utils.service;

import br.com.bank.api.utils.dto.ResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class DtoService {

    public static <D> ResponseDto.Body.Response okResponseDto(Object object, Class<D> dtoClass){
        D objectDto = DtoService.entityToDto(object, dtoClass);
        return new ResponseDto.Body.Response(HttpStatus.OK.value(), objectDto);
    }

    public static <D> ResponseDto.Body.Response createdResponseDto(Object object, Class<D> dtoClass){
        D objectDto = DtoService.entityToDto(object, dtoClass);
        return new ResponseDto.Body.Response(HttpStatus.CREATED.value(), objectDto);
    }

    private static ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        return modelMapper;
    }

    private static <E, D> D entityToDto(E entity, Class<D> dtoClass){
        return getModelMapper().map(entity, dtoClass);
    }

    public static <D, E> E dtoToEntity(D dto, Class<E> entityClass){
        return getModelMapper().map(dto, entityClass);
    }

    public static <E, D> List<D> entitysToDtos(List<E> entitys, Class<D> dtoClass){
        return entitys.stream()
                .map(entity -> entityToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

}
