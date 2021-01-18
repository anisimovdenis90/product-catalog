package com.anisimov.denis.controllers;

import com.anisimov.denis.entities.Product;
import com.anisimov.denis.exceptions.InputDataError;
import com.anisimov.denis.services.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Api(value = "/products", tags = {"Каталог продуктов"})
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation(
            value = "Возвращает список продуктов",
            httpMethod = "GET",
            produces = "application/json",
            response = Product.class,
            responseContainer = "Page"
    )
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "Номер страницы для отображения", name = "p", example = "1", type = "Integer", required = true),
            @ApiImplicitParam(value = "Слово, содержащееся в названии продукта", name = "name", example = "Milk", type = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "1", name = "p") Integer page,
                                        @RequestParam(name = "name", required = false) String name
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAllProducts(name, page - 1, 5);
    }

    @DeleteMapping("/{product_id}")
    @ApiOperation(
            value = "Удаляет продукт с указанным id",
            httpMethod = "DELETE"
    )
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "Id продукта, который должен быть удален", name = "product_id", example = "1", type = "Long", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Неверный id продукта"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "product_id") Long id) {
        return productService.deleteProductById(id);
    }

    @PostMapping(consumes = "application/json")
    @ApiOperation(
            value = "Сохраняет указанный продукт в БД",
            httpMethod = "POST",
            consumes = "application/json"
    )
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "Продукт, который должен быть сохранен", name = "product", dataTypeClass = Product.class, required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Продукт успешно создан"),
            @ApiResponse(code = 400, message = "Неверное имя продукта (минимум 3 символа)"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<?> saveProduct(@RequestBody @Validated Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new InputDataError(bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        product.setId(null);
        return productService.saveProduct(product);
    }

    @PutMapping(value = "/{product_id}")
    @ApiOperation(
            value = "Изменяет описание продукта с указанным id",
            httpMethod = "PUT"
    )
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "Id продукта, описание которого требуется изменить", name = "product_id", dataTypeClass = Long.class, required = true),
            @ApiImplicitParam(value = "Новое описание продукта", name = "description", dataTypeClass = String.class, required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Неверный id продукта"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<?> updateProductById(@PathVariable(name = "product_id") Long id, @RequestParam String description) {
        return productService.changeDescriptionById(description, id);
    }
}
