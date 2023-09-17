package com.wakacast.repositories.criteri_class;

import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import com.wakacast.models.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class TalentSearchCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ModelMapper mapper;

    public TalentSearchCriteriaRepository(EntityManager entityManager, ModelMapper mapper) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.mapper = mapper;
    }

    public Page<UserResponseDto> findTalent(TalentSearchCriteria talentSearchCriteria) {
        TalentPage talentPage = new TalentPage();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        Predicate predicate = getPredicate(talentSearchCriteria, userRoot);

        criteriaQuery.where(predicate).distinct(true);
        criteriaQuery.select(userRoot).distinct(true);
        setOrder(talentPage, criteriaQuery, userRoot);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(talentPage.getPageNumber() * talentPage.getPageSize());
        typedQuery.setMaxResults(talentPage.getPageSize());

        Pageable pageable = getPageable(talentPage);

        long talentSearchCount = getTalentRollCount(talentSearchCriteria);

        List<UserResponseDto> result = typedQuery.getResultList().stream()
                .map(response -> mapper.map(response, UserResponseDto.class)).collect(Collectors.toList());
        return new PageImpl<>(result, pageable, talentSearchCount);
    }

    private Predicate getPredicate(TalentSearchCriteria talentSearchCriteria,
                                   Root<User> userRoot) {
        SetJoin<User, Role> userRoleSetJoin = userRoot.join(User_.roles);
        SetJoin<User, Genre> userGenreSetJoin = userRoot.join(User_.genres);

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(talentSearchCriteria.getKeyword())) {
            predicates.add(
                    criteriaBuilder.like(userRoleSetJoin.get(Role_.roleTitle),"%" + talentSearchCriteria.getKeyword() + "%")
            );
        }

        if (Objects.nonNull(talentSearchCriteria.getAllAuditionsAndJob())) {

            predicates.add(
                    criteriaBuilder.like(userGenreSetJoin.get(Genre_.genreTitle),"%" + talentSearchCriteria.getAllAuditionsAndJob() + "%")
                    );
        }

        if (Objects.nonNull(talentSearchCriteria.getGender())) {

            predicates.add(
                    criteriaBuilder.equal(userRoot.get(User_.GENDER),
                            talentSearchCriteria.getGender())
            );
        }

        if (Objects.nonNull(talentSearchCriteria.getState())) {

            predicates.add(
                    criteriaBuilder.equal(userRoot.get(User_.STATE),
                            talentSearchCriteria.getState())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(TalentPage talentPage, CriteriaQuery<User> criteriaQuery,
                          Root<User> userRoot) {
            if(talentPage.getSortDirection().equals(Sort.Direction.ASC)){
                criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(talentPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(talentPage.getSortBy())));
            }
    }

    private Pageable getPageable(TalentPage talentPage) {
        Sort sort = Sort.by(talentPage.getSortDirection(), talentPage.getSortBy());
        return PageRequest.of(talentPage.getPageNumber(), talentPage.getPageSize(), sort);
    }

    private long getTalentRollCount(TalentSearchCriteria searchCriteria) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(getPredicate(searchCriteria, countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
