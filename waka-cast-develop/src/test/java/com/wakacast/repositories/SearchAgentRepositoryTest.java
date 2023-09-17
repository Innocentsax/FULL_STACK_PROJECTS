package com.wakacast.repositories;

import com.wakacast.models.SearchAgent;
import com.wakacast.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SearchAgentRepositoryTest {
    private final SearchAgentRepository searchAgentRepository;
    private final UserRepository userRepository;

    private SearchAgent searchAgent;
    private User user;
    @Autowired
    public SearchAgentRepositoryTest(SearchAgentRepository searchAgentRepository, UserRepository userRepository) {
        this.searchAgentRepository = searchAgentRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("og@gmail.com");
        searchAgent = new SearchAgent();
        searchAgent.setUser(user);
    }

    @Test
    void saveSearchAgent() {
        SearchAgent savedSearchAgent = searchAgentRepository.save(searchAgent);
        assertThat(savedSearchAgent).isNotNull().isEqualTo(searchAgent);
    }

    @Test
    void findSearchAgentByIdAndUserEmail() {
        userRepository.save(user);
        searchAgentRepository.save(searchAgent);
        SearchAgent returnedSearchAgent = searchAgentRepository.findSearchAgentByIdAndUserEmail(1L, "og@gmail.com").get();
        assertThat(returnedSearchAgent).isNotNull().isEqualTo(searchAgent);
    }
}