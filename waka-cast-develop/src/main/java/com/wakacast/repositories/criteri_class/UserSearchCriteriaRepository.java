package com.wakacast.repositories.criteri_class;

import com.wakacast.dto.DailyEmailDTO;
import com.wakacast.dto.UserRoleSearchDTO;
import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import com.wakacast.models.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class UserSearchCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ModelMapper mapper;

    public UserSearchCriteriaRepository(EntityManager entityManager, ModelMapper mapper) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.mapper = mapper;
    }

    public List<User> findUsers(DailyEmailDTO emailDTO) {
        CriteriaQuery<User> criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        Predicate predicate = getPredicate(emailDTO, userRoot);

        criteriaQuery.where(predicate).distinct(true);
        criteriaQuery.select(userRoot).distinct(true);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);

        return new ArrayList<>(typedQuery.getResultList());
    }
//Logic to get user by role start ********
    public Page<UserResponseDto> findUserByRole (UserRoleSearchDTO userRoleSearchDTO) {
        TalentPage talentPage = new TalentPage();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        Predicate predicate = getPredicateForUserByRole(userRoleSearchDTO, userRoot);

        criteriaQuery.where(predicate).distinct(true);
        criteriaQuery.select(userRoot).distinct(true);
        setOrder(talentPage, criteriaQuery, userRoot);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(talentPage.getPageNumber() * talentPage.getPageSize());
        typedQuery.setMaxResults(talentPage.getPageSize());

        Pageable pageable = getPageable(talentPage);

        long talentSearchCount = getUserCount(userRoleSearchDTO);

        List<UserResponseDto> result = typedQuery.getResultList().stream()
                .map(response -> mapper.map(response, UserResponseDto.class)).collect(Collectors.toList());
        return new PageImpl<>(result, pageable, talentSearchCount);
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
    private long getUserCount(UserRoleSearchDTO userRoleSearchDTO) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(getPredicateForUserByRole(userRoleSearchDTO, countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Predicate getPredicateForUserByRole(UserRoleSearchDTO userRoleSearchDTO,
                                   Root<User> userRoot) {
        SetJoin<User, Role> userRoleSetJoin = userRoot.join(User_.roles);
        List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    criteriaBuilder.equal(userRoleSetJoin.get(Role_.roleTitle), userRoleSearchDTO.getRoleOfUser().toUpperCase())
            );

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
    //Logic to get user by role End ********


    private Predicate getPredicate(DailyEmailDTO emailDTO,
                                   Root<User> userRoot) {
        SetJoin<User, UserPersona> userUserPersonaSetJoin = userRoot.join(User_.userPersonas);
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(emailDTO.getUserPersona())) {
            predicates.add(
                    criteriaBuilder.like(userUserPersonaSetJoin.get(UserPersona_.persona),"%" + emailDTO.getUserPersona() + "%")
            );
        }

        if (Objects.nonNull(emailDTO.getUserType())) {
            predicates.add(
                    criteriaBuilder.equal(userRoot.get(User_.USER_TYPE),
                            emailDTO.getUserType())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
