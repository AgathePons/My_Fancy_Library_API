package com.example.demo.dto.dtomapper;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryWithBooksDto;
import com.example.demo.entities.Category;
import com.example.demo.modelmapper.BookMapper;
import com.example.demo.modelmapper.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryWithBooksDtoMapper {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryWithBooksDto> convert(Iterable<Category> categoriesIterable) {
        List<Category> categoryList = ModelMapperUtil.mapList(categoriesIterable, Category.class);
        List<CategoryWithBooksDto> test = null;

        return categoryList.stream().map(
                category -> {
                    CategoryWithBooksDto categoryWithBooksDto = modelMapper.map(category, CategoryWithBooksDto.class);
                    List<BookDto> bookDtoList = category.getBooks().stream().map(
                            book -> bookMapper.convertToDto(book)
                    ).toList();
                    categoryWithBooksDto.setBooks(bookDtoList);
                    return categoryWithBooksDto;
                }
        ).toList();
    }
}
