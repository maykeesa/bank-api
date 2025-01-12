package br.com.bank.api.core.service;

import br.com.bank.api.account.dto.BankAccountDto;
import br.com.bank.api.core.dto.ResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class DtoService {

    private static ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        return modelMapper;
    }

    public static <E, D> D entityToDto(E entity, Class<D> dtoClass){
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

    public static <D, E> List<E> dtosToEntitys(List<D> dtos, Class<E> entityClass){
        return dtos.stream()
                .map(dto -> entityToDto(dto, entityClass))
                .collect(Collectors.toList());
    }

    public static <D> ResponseDto.Body.Response getResponseDto(Object object, Class<D> dtoClass){
        D objectDto = DtoService.entityToDto(object, dtoClass);
        return new ResponseDto.Body.Response(HttpStatus.CREATED.value(), objectDto);
    }
}
