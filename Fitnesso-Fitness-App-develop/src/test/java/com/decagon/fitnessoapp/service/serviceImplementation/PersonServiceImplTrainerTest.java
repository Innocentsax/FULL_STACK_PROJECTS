//package com.decagon.fitnessoapp.service.serviceImplementation;
//
//import com.decagon.fitnessoapp.dto.PersonRequest;
//import com.decagon.fitnessoapp.dto.PersonResponse;
//import com.decagon.fitnessoapp.model.user.Person;
//import com.decagon.fitnessoapp.model.user.ROLE_DETAIL;
//import com.decagon.fitnessoapp.repository.PersonRepository;
//import java.util.Optional;
//import org.junit.jupiter.api.*;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//@SpringBootTest(classes = {PersonServiceImplTrainerTest.class})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class PersonServiceImplTrainerTest {
//
//    @Mock
//    PersonRepository personRepository;
//
//    @Mock
//   ModelMapper modelMapper;
//
//    @InjectMocks
//    PersonServiceImpl personServiceImpl;
//
//    PersonRequest personRequest;
//    Person person2;
//    PersonResponse personResponse;
//
//    @BeforeEach
//    void setUp() {
//        person2 = new Person();
//        person2.setId(2L);
//        person2.setPassword("123456789");
//        person2.setRoleDetail(ROLE_DETAIL.TRAINER);
//        person2.setEmail("chika@gmail.com");
//        person2.setFirstName("Chika");
//
//
//        personRequest = new PersonRequest();
//        personRequest.setEmail("chika@gmail.com");
//        personRequest.setFirstName("chika");
//        personRequest.setLastName("odo");
//        personRequest.setPassword("123456789");
//
//        personResponse = new PersonResponse();
//        personResponse.setFirstName("emeka");
//        personResponse.setLastName("enyiocha");
//        personResponse.setEmail("chika@gmail.com");
//    }
//
//
//
//    @Test
//    @Order(1)
//    void addTrainer(){
//
//        when(personRepository.findByEmail(person2.getEmail())).thenReturn(Optional.of(person2));
//        when(personRepository.save(person2)).thenReturn(person2);
//        when(modelMapper.map(person2, PersonResponse.class)).thenReturn(personResponse);
//
//        personServiceImpl.addTrainer(personRequest);
//
//        verify(personRepository, times(1)).save(person2);
//        verify(personRepository, times(1)).findByEmail(person2.getEmail());
//
//    }
//
//    @Test
//    @Order(2)
//    void removeTrainer() {
//
//        when(personRepository.getById(person2.getId())).thenReturn(person2);
//        final ResponseEntity<String> result = personServiceImpl.removeTrainer(2L);
//
//        verify(personRepository, times(1)).delete(person2);
//        verify(personRepository, times(1)).getById(person2.getId());
//
//        assertEquals(result, personServiceImpl.removeTrainer(2L));
//    }
//}