package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bulldog.eshop.dto.CategoryDTO;
import ru.bulldog.eshop.model.Category;
import ru.bulldog.eshop.service.CategoryService;

import javax.persistence.EntityNotFoundException;

import static ru.bulldog.eshop.util.EntityConverter.CATEGORY_TO_DTO_FACTORY;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/{id}")
	public CategoryDTO getCategory(@PathVariable long id) {
		Category category = categoryService.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found, id: " + id));
		return CATEGORY_TO_DTO_FACTORY.apply(category);
	}
}
