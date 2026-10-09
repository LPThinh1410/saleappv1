package com.example.saleappv1;

import com.example.saleappv1.models.Category;
import com.example.saleappv1.models.Product;
import com.example.saleappv1.service.CategoryService;
import com.example.saleappv1.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Saleappv1ApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Test
    void contextLoads() {
    }

    @Test
    void testCategoryAndProductCrud() {
        // 1. Thêm danh mục
        Category cat = new Category();
        cat.setName("Điện Thoại Test");
        categoryService.addCategory(cat);
        Assertions.assertNotNull(cat.getId());

        // 2. Tìm danh mục
        Category foundCat = categoryService.getCategoryById(cat.getId()).orElse(null);
        Assertions.assertNotNull(foundCat);
        Assertions.assertEquals("Điện Thoại Test", foundCat.getName());

        // 3. Cập nhật danh mục
        foundCat.setName("Điện Thoại Đã Sửa");
        categoryService.updateCategory(foundCat);
        Category updatedCat = categoryService.getCategoryById(cat.getId()).orElse(null);
        Assertions.assertNotNull(updatedCat);
        Assertions.assertEquals("Điện Thoại Đã Sửa", updatedCat.getName());

        // 4. Thêm sản phẩm
        Product prod = new Product();
        prod.setName("iPhone 16 Pro Max");
        prod.setPrice(34990000.0);
        prod.setDescription("Sản phẩm cao cấp mới nhất");
        prod.setCategory(updatedCat);
        productService.addProduct(prod);
        Assertions.assertNotNull(prod.getId());

        // 5. Tìm sản phẩm
        Product foundProd = productService.getProductById(prod.getId()).orElse(null);
        Assertions.assertNotNull(foundProd);
        Assertions.assertEquals("iPhone 16 Pro Max", foundProd.getName());
        Assertions.assertEquals(updatedCat.getId(), foundProd.getCategory().getId());

        // 6. Cập nhật sản phẩm
        foundProd.setPrice(32990000.0);
        productService.updateProduct(foundProd);
        Product updatedProd = productService.getProductById(prod.getId()).orElse(null);
        Assertions.assertNotNull(updatedProd);
        Assertions.assertEquals(32990000.0, updatedProd.getPrice());

        // 7. Xóa sản phẩm
        productService.deleteProductById(prod.getId());
        Assertions.assertFalse(productService.getProductById(prod.getId()).isPresent());

        // 8. Xóa danh mục
        categoryService.deleteCategoryById(cat.getId());
        Assertions.assertFalse(categoryService.getCategoryById(cat.getId()).isPresent());
    }
}
