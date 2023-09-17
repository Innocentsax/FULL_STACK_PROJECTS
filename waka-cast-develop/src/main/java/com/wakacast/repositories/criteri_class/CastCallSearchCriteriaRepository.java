package com.wakacast.repositories.criteri_class;

import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.search_criteria.CastCallSearchCriteria;
import com.wakacast.models.*;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CastCallSearchCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CastCallSearchCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<CastCall> findMatchingRoles(CastCallPage castCallPage,
                                              RoleMatchSearchCriteria roleMatchSearchCriteria) {
        CriteriaQuery<CastCall> criteriaQuery = criteriaBuilder.createQuery(CastCall.class);
        Root<CastCall> castCallRoot = criteriaQuery.from(CastCall.class);
        Predicate predicateForMatchingRoles = getPredicateForMatchingRole(roleMatchSearchCriteria, castCallRoot );
        return getCastCalls(castCallPage, criteriaQuery, castCallRoot, predicateForMatchingRoles);
    }

    private Predicate getPredicateForMatchingRole(RoleMatchSearchCriteria roleMatchSearchCriteria,
                                                  Root<CastCall> castCallRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(roleMatchSearchCriteria.getProjectName())) {

            predicates.add(
                    criteriaBuilder.like(castCallRoot.get(CastCall_.PROJECT_NAME),
                            "%" + roleMatchSearchCriteria.getProjectName() + "%")
            );
        }

        if (Objects.nonNull(roleMatchSearchCriteria.getProjectType())) {
            predicates.add(
                    criteriaBuilder.like(castCallRoot.get(CastCall_.PROJECT_TYPE),
                            "%" + roleMatchSearchCriteria.getProjectType() + "%")
            );
        }

        if (Objects.nonNull(roleMatchSearchCriteria.getTalentSkill())) {
            predicates.add(
                    criteriaBuilder.like(castCallRoot.get(CastCall_.TALENT_SKILL),
                            "%" + roleMatchSearchCriteria.getTalentSkill() + "%")
            );
        }

        if (Objects.nonNull(roleMatchSearchCriteria.getGender())) {
            predicates.add(
                    criteriaBuilder.equal(castCallRoot.get(CastCall_.GENDER),
                            roleMatchSearchCriteria.getGender())
            );
        }

        if (Objects.nonNull(roleMatchSearchCriteria.getCountry())) {
            predicates.add(
                    criteriaBuilder.like(castCallRoot.get(CastCall_.COUNTRY),
                            "%" + roleMatchSearchCriteria.getCountry() + "%")
            );
        }
        if (Objects.nonNull(roleMatchSearchCriteria.getState())) {
            predicates.add(
                    criteriaBuilder.like(castCallRoot.get(CastCall_.STATE),
                            "%" + roleMatchSearchCriteria.getState() + "%")
            );
        }

        if (Objects.nonNull(roleMatchSearchCriteria.getCity())) {
            predicates.add(
                    criteriaBuilder.like(castCallRoot.get(CastCall_.CITY),
                            "%" + roleMatchSearchCriteria.getCity() + "%")
            );
        }

        if (Objects.nonNull(roleMatchSearchCriteria.getPostalZipCode())) {
            predicates.add(
                    criteriaBuilder.like(castCallRoot.get(CastCall_.POSTAL_ZIP_CODE),
                            "%" + roleMatchSearchCriteria.getPostalZipCode() + "%")
            );
        }

        if (Objects.nonNull(roleMatchSearchCriteria.getLanguages())) {
            predicates.add(
                    criteriaBuilder.like(castCallRoot.get(CastCall_.LANGUAGES),
                            "%" + roleMatchSearchCriteria.getLanguages() + "%")
            );
        }

        if (roleMatchSearchCriteria.getMinAge() > 0) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(castCallRoot.get(CastCall_.minAge),
                            roleMatchSearchCriteria.getMinAge())
            );
        }

        if (roleMatchSearchCriteria.getMaxAge() > 0) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(castCallRoot.get(CastCall_.maxAge),
                            roleMatchSearchCriteria.getMaxAge())
            );
        }
        predicates.add(
                criteriaBuilder.equal(castCallRoot.get(CastCall_.IS_REPORTED_ENOUGH), false)
        );

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(CastCallPage castCallPage, CriteriaQuery<CastCall> criteriaQuery,
                          Root<CastCall> castCallRoot) {
        if(castCallPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(castCallRoot.get(castCallPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(castCallRoot.get(castCallPage.getSortBy())));
        }
    }

    private Pageable getPageable(CastCallPage castCallPage) {
        Sort sort = Sort.by(castCallPage.getSortDirection(), castCallPage.getSortBy());
        return PageRequest.of(castCallPage.getPageNumber(), castCallPage.getPageSize(), sort);
    }

    private long getCastCallRollCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<CastCall> countRoot = countQuery.from(CastCall.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }


    public Page<CastCall> findCastCall(CastCallPage castCallPage,
                                            CastCallSearchCriteria castCallSearchCriteria) {
        CriteriaQuery<CastCall> criteriaQuery = criteriaBuilder.createQuery(CastCall.class);
        Root<CastCall> castCallRoot = criteriaQuery.from(CastCall.class);
        Predicate predicateForCastCallSearch = getPredicateForSearchCastCall(castCallSearchCriteria, castCallRoot );
        return getCastCalls(castCallPage, criteriaQuery, castCallRoot, predicateForCastCallSearch);
    }

    private Page<CastCall> getCastCalls(CastCallPage castCallPage, CriteriaQuery<CastCall> criteriaQuery, Root<CastCall> castCallRoot, Predicate predicateForCastCallSearch) {
        criteriaQuery.where(predicateForCastCallSearch);
        setOrder(castCallPage, criteriaQuery, castCallRoot);

        TypedQuery<CastCall> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(castCallPage.getPageNumber() * castCallPage.getPageSize());
        typedQuery.setMaxResults(castCallPage.getPageSize());

        Pageable pageable = getPageable(castCallPage);

        long roleSearchCount = getCastCallRollCount(predicateForCastCallSearch);

        return new PageImpl<>(typedQuery.getResultList(), pageable, roleSearchCount);
    }

    private Predicate getPredicateForSearchCastCall(CastCallSearchCriteria castCallSearchCriteria,
                                                  Root<CastCall> castCallRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(castCallSearchCriteria.getProjectName())) {
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.upper(castCallRoot.get(CastCall_.projectName)),
                            "%" + castCallSearchCriteria.getProjectName().toUpperCase() + "%")
            );
        }

        if (Objects.nonNull(castCallSearchCriteria.getProjectType())) {
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.upper(castCallRoot.get(CastCall_.projectType)),
                            "%" + castCallSearchCriteria.getProjectType().toUpperCase() + "%")
            );
        }

        predicates.add(
                criteriaBuilder.equal(castCallRoot.get(CastCall_.IS_REPORTED_ENOUGH), false)
        );

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
