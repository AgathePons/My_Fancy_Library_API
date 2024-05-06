package com.example.demo.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ModelMapperUtil {

    private static ModelMapper modelMapper = new ModelMapper();

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
        return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
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
        return ModelMapperUtil.toStream(source).map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }

    /**
     *
     * @param iterable Iterable<T> to stream
     * @return Stream<T>
     * @param <T> type in the Iterable
     */
    private static <T> Stream<T> toStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
