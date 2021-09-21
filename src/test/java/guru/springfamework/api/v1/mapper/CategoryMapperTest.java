package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final String CATEGORY_NAME = "Something";
    public static final long ID = 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        category.setId(ID);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(ID, categoryDTO.getId());
        assertEquals(CATEGORY_NAME, categoryDTO.getName());
    }
}