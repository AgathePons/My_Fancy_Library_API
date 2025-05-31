package com.example.demo.modelmapper;

import com.example.demo.dto.BookDto;
import com.example.demo.entities.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ModelMapperUtil {
    private static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private BookMapper bookMapper;

    private  ModelMapperUtil() {}

    /**
     * Convert a List of type S into a list of type T
     * @param source List<S> source to be converted
     * @param targetClass Class to convert in
     * @return List<T> converted List
     * @param <S> type of source
     * @param <T> type to convert in
     */
    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream().map(element -> modelMapper.map(element, targetClass)).toList();
    }

    /**
     * Convert an Iterable of a type S into a list of a type T
     * @param source Iterable<S> source to be converted
     * @param targetClass Class to convert in
     * @return List<T> converted List
     * @param <S> type of source
     * @param <T> type to convert in
     */
    public static <S, T> List<T> mapList(Iterable<S> source, Class<T> targetClass) {
        return ModelMapperUtil.toStream(source).map(element -> modelMapper.map(element, targetClass)).toList();
    }

    /**
     * Stream an Iterable
     * @param iterable Iterable<T> to stream
     * @return Stream<T>
     * @param <T> type in the Iterable
     */
    private static <T> Stream<T> toStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Convert an Iterable<Book> into a List<BookDto> through a BookMapper
     * @param source Iterable<Book>
     * @param mapper the specific mapper to use
     * @return List<BookDto>
     */
    public static List<BookDto> mapListBookMapper(Iterable<Book> source, BookMapper mapper) {
        List<Book> list = ModelMapperUtil.toStream(source).toList();
        return list.stream().map(book -> {
            Book bookEntity = book;
            return mapper.convertToDto(bookEntity);
        }).toList();
    }
}
