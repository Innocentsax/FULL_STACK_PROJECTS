//package com.wakacast.repositories;
//
//import com.wakacast.dto.pages_criteria.CastCallPage;
//import com.wakacast.dto.search_criteria.CastCallSearchCriteria;
//import com.wakacast.enums.Gender;
//import com.wakacast.models.CastCall;
//import com.wakacast.models.User;
//import com.wakacast.repositories.criteri_class.CastCallSearchCriteriaRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import javax.persistence.EntityManager;
//import javax.persistence.criteria.CriteriaBuilder;
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//class CastCallRepositoryTest {
//    private final CastCallRepository castCallRepository;
//    private final UserRepository userRepository;
//    private final CastCallSearchCriteriaRepository searchCriteriaRepository;
//    private final EntityManager entityManager;
//
//    private User user;
//    private User user2;
//    private CastCall castCall;
//    private CastCall castCall2;
//    private Pageable page;
//
//    @Autowired
//    CastCallRepositoryTest(CastCallRepository castCallRepository, UserRepository userRepository, CastCallSearchCriteriaRepository searchCriteriaRepository, EntityManager entityManager) {
//        this.castCallRepository = castCallRepository;
//        this.userRepository = userRepository;
//        this.searchCriteriaRepository = searchCriteriaRepository;
//        this.entityManager = entityManager;
//    }
//
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setEmail("onychris@gmail.com");
//        user.setGender(Gender.FEMALE);
//        user.setPassword("ONYchris@007");
//
//        user2 = new User();
//        user2.setEmail("test@gmail.com");
//        user2.setGender(Gender.MALE);
//        user2.setPassword("ONYchris@007");
//
//        castCall = new CastCall();
//        castCall.setProjectName("Wakcast TV series");
//        castCall.setProjectType("TV series");
//        castCall.setTalentSkill("Actor");
////        castCall.setLocation("Lagos, Nigeria");
//        castCall.setPostExpiryDate(LocalDate.now());
//        castCall.setPublisher(user);
//        userRepository.save(user);
//        castCallRepository.save(castCall);
//
//        castCall2 = new CastCall();
//        castCall2.setProjectName("Test Project");
//        castCall2.setProjectType("Test");
//        castCall2.setTalentSkill("Actor");
//        castCall2.setPostExpiryDate(LocalDate.now());
//        castCall2.setPublisher(user2);
//        userRepository.save(user2);
//        castCallRepository.save(castCall2);
//    }
//
//    @Test
//    void findCastCallByPublisherId() {
//        CastCall existingCastCall = castCallRepository.findCastCallByPublisherId(user.getId()).get(0);
//        assertThat(existingCastCall).isNotNull();
//    }
//
//    @Test
//    void findCastCallByPublisherEmailAndId() {
//        CastCall existingCastCall = castCallRepository.findCastCallByPublisherEmailAndId(user.getEmail(), user.getId());
//        assertThat(existingCastCall).isNotNull();
//    }
//
//    @Test
//    void save() {
//        CastCall existingCastCall = castCallRepository.save(castCall);
//        assertEquals(existingCastCall, castCall);
//    }
//
//    @Test
//    void findCastCallsByPostExpiryDateBefore() {
//        Page<CastCall> castCallPage = castCallRepository.findCastCallsByPostExpiryDateBefore(page);
//        assertThat(castCallPage).isNotNull();
//        assertThat(castCallPage.getTotalElements()).isEqualTo(2);
//    }
//
//    @Test
//    void searchCastCall() {
//        CastCallPage castCallPage = new CastCallPage();
//        CastCallSearchCriteria criteria = new CastCallSearchCriteria();
//        criteria.setProjectName("es");
//        Page<CastCall> castCall = searchCriteriaRepository.findCastCall(castCallPage, criteria);
////        assertThat(castCall).isNotNull();
////        assertThat(castCallPage.getTotalElements()).isEqualTo(2);
//    }
//
////    @Test
////    void findCastCallsByCreateDateIsAfterAndTalentSkillContaining() {
////        List<CastCall> castCalls = castCallRepository
////                .findCastCallsByCreateDateIsAfterAndTalentSkillContaining(
////                LocalDateTime.now().minusHours(1), "Actor"
////        );
////        assertThat(castCalls).isNotNull();
////        assertThat(castCalls).contains(castCall);
////    }
////
////    @Test
////    void findCastCallsByCreateDateIsAfterAndTalentSkillContainingAndLocationContaining() {
////        List<CastCall> castCalls = castCallRepository
////                .findCastCallsByCreateDateIsAfterAndTalentSkillContainingAndLocationContaining(
////                LocalDateTime.now().minusHours(1), "Actor", "Lagos"
////        );
////        assertThat(castCalls).isNotNull();
////        assertThat(castCalls).contains(castCall);
////    }
//}
