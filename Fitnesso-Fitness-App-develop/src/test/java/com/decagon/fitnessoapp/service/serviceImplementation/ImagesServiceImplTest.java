//package com.decagon.fitnessoapp.service.serviceImplementation;
//
//import com.decagon.fitnessoapp.model.product.Image;
//import com.decagon.fitnessoapp.repository.ImagesRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ContextConfiguration(classes = {ImagesServiceImpl.class})
//@ExtendWith(SpringExtension.class)
//class ImagesServiceImplTest {
//    @MockBean
//    private ImagesRepository imagesRepository;
//
//    @Autowired
//    private ImagesServiceImpl imagesServiceImpl;
//
//    @MockBean
//    private ModelMapper modelMapper;
//
//    Optional<List<Image>> ofResult;
//
//    @BeforeEach
//    void test() {
//        Image image = new Image();
//        image.setId(123L);
//        image.setImageName("No Image for this product");
//        image.setImageUrl("https://example.org/example");
//        image.setProductName("No Image for this product");
//
//        ArrayList<Image> imageList = new ArrayList<>();
//        imageList.add(image);
//        ofResult = Optional.of(imageList);
//    }
//
//    @Disabled
//    @Test
//    void testAddImage(){
//
//    }
//
//    @Test
//    void testGetImage() {
//        doNothing().when(this.modelMapper).map((Object) any(), (Object) any());
//
//        when(this.imagesRepository.findByProductName((String) any())).thenReturn(ofResult);
//        this.imagesServiceImpl.getImage("Product Name");
//        verify(this.modelMapper).map((Object) any(), (Object) any());
//        verify(this.imagesRepository).findByProductName((String) any());
//    }
//
//    @Test
//    void testGetAllImage() {
//        doNothing().when(this.modelMapper).map((Object) any(), (Object) any());
//
//        when(this.imagesRepository.findAllByProductName((String) any())).thenReturn(ofResult);
//        assertEquals(1, this.imagesServiceImpl.getAllImage("Product Name").size());
//        verify(this.modelMapper).map((Object) any(), (Object) any());
//        verify(this.imagesRepository).findAllByProductName((String) any());
//    }
//}
//
