package com.lld4.productservice.controllers;

    import com.lld4.productservice.auth.AuthUtils;
    import com.lld4.productservice.dtos.UserDto;
    import com.lld4.productservice.exceptions.InvalidProductException;
    import com.lld4.productservice.models.Product;
    import com.lld4.productservice.models.Role;
    import com.lld4.productservice.models.Token;
    import com.lld4.productservice.services.ProductService;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;

    import java.util.List;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertThrows;
    import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    class ProductControllerTest {

        @Mock
        private ProductService productService;

        @Mock
        private AuthUtils authUtils;

        @InjectMocks
        private ProductController productController;

        @Test
        void getProductById_returnsProduct_whenIdIsValid() {
            Long productId = 1L;
            Product product = new Product();
            product.setId(productId);

            when(productService.getProductById(productId)).thenReturn(product);

            ResponseEntity<Product> response = productController.getProductById(productId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(product, response.getBody());
        }

        @Test
        void getProductById_throwsException_whenIdIsInvalid() {
            Long invalidId = 0L;

            assertThrows(InvalidProductException.class, () -> productController.getProductById(invalidId));
        }

        @Test
        void getProductByIdV1_returnsUnauthorized_whenTokenIsInvalid() {
            Token token = new Token();
            when(authUtils.validateToken(token)).thenReturn(null);

            ResponseEntity<Product> response = productController.getProductByIdV1(token, 1L);

            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        void getProductByIdV1_returnsForbidden_whenUserIsNotAdmin() {
            Token token = new Token();
            UserDto userDto = new UserDto();
            userDto.setRoles(List.of(new Role("USER")));
            when(authUtils.validateToken(token)).thenReturn(userDto);

            ResponseEntity<Product> response = productController.getProductByIdV1(token, 1L);

            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        void getProductByIdV1_returnsProduct_whenUserIsAdmin() {
            Token token = new Token();
            UserDto userDto = new UserDto();
            userDto.setRoles(List.of(new Role("ADMIN")));
            Product product = new Product();
            product.setId(1L);

            when(authUtils.validateToken(token)).thenReturn(userDto);
            when(productService.getProductById(1L)).thenReturn(product);

            ResponseEntity<Product> response = productController.getProductByIdV1(token, 1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(product, response.getBody());
        }

        @Test
        void getAllProducts_returnsUnauthorized_whenTokenIsInvalid() {
            Token token = new Token();
            when(authUtils.validateToken(token)).thenReturn(null);

            ResponseEntity<List<Product>> response = productController.getAllProducts(token);

            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        void getAllProducts_returnsForbidden_whenUserIsNotAdmin() {
            Token token = new Token();
            UserDto userDto = new UserDto();
            userDto.setRoles(List.of(new Role("USER")));
            when(authUtils.validateToken(token)).thenReturn(userDto);

            ResponseEntity<List<Product>> response = productController.getAllProducts(token);

            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        void getAllProducts_returnsProductList_whenUserIsAdmin() {
            Token token = new Token();
            UserDto userDto = new UserDto();
            userDto.setRoles(List.of(new Role("ADMIN")));
            List<Product> products = List.of(new Product(), new Product());

            when(authUtils.validateToken(token)).thenReturn(userDto);
            when(productService.getAllProducts()).thenReturn(products);

            ResponseEntity<List<Product>> response = productController.getAllProducts(token);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(products, response.getBody());
        }
    }