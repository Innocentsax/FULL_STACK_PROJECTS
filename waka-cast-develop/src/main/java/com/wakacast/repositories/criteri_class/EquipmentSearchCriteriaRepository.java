package com.wakacast.repositories.criteri_class;

import com.wakacast.dto.pages_criteria.EquipmentPage;
import com.wakacast.dto.search_criteria.EquipmentSearchCriteria;
import com.wakacast.models.Equipment;
import com.wakacast.models.Equipment_;
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
public class EquipmentSearchCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;


    public EquipmentSearchCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Equipment> findEquipmentWithFilter(EquipmentSearchCriteria equipmentSearchCriteria) {
        EquipmentPage equipmentPage = new EquipmentPage();
        CriteriaQuery<Equipment> criteriaQuery = criteriaBuilder.createQuery(Equipment.class);
        Root<Equipment> equipmentRoot = criteriaQuery.from(Equipment.class);
        Predicate predicate = getPredicate(equipmentSearchCriteria, equipmentRoot );
        criteriaQuery.where(predicate);
        setOrder(equipmentPage, criteriaQuery, equipmentRoot);

        TypedQuery<Equipment> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(equipmentPage.getPageNumber() * equipmentPage.getPageSize());
        typedQuery.setMaxResults(equipmentPage.getPageSize());

        Pageable pageable = getPageable(equipmentPage);

        long equipmentSearchCount = getEquipmentCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, equipmentSearchCount);
    }

    private Predicate getPredicate(EquipmentSearchCriteria equipmentSearchCriteria,
                                                  Root<Equipment> equipmentRoot) {

        List<Predicate> predicate = new ArrayList<>();

        if (Objects.nonNull(equipmentSearchCriteria.getQuantity())) {

            predicate.add(
                    criteriaBuilder.equal(equipmentRoot.get(Equipment_.QUANTITY),
                            equipmentSearchCriteria.getQuantity())
            );
        }

        if (Objects.nonNull(equipmentSearchCriteria.getCity())) {

            predicate.add(
                    criteriaBuilder.equal(equipmentRoot.get(Equipment_.CITY),
                            equipmentSearchCriteria.getCity().toUpperCase())
            );
        }


        if (Objects.nonNull(equipmentSearchCriteria.getState())) {

            predicate.add(
                    criteriaBuilder.equal(equipmentRoot.get(Equipment_.STATE),
                            equipmentSearchCriteria.getState().toUpperCase())
            );
        }


        if (Objects.nonNull(equipmentSearchCriteria.getTypeOfAsset())) {

            predicate.add(
                    criteriaBuilder.like(equipmentRoot.get(Equipment_.TYPE_OF_ASSET),
                            "%" + equipmentSearchCriteria.getTypeOfAsset().toUpperCase() + "%")
            );
        }


        return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
    }



    private void setOrder(EquipmentPage equipmentPage, CriteriaQuery<Equipment> criteriaQuery,
                          Root<Equipment> equipmentRoot) {
            if(equipmentPage.getSortDirection().equals(Sort.Direction.ASC)){
                criteriaQuery.orderBy(criteriaBuilder.asc(equipmentRoot.get(equipmentPage.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(equipmentRoot.get(equipmentPage.getSortBy())));
            }
    }



    private Pageable getPageable(EquipmentPage equipmentPage) {
        Sort sort = Sort.by(equipmentPage.getSortDirection(), equipmentPage.getSortBy());
        return PageRequest.of(equipmentPage.getPageNumber(), equipmentPage.getPageSize(), sort);
    }

    private long getEquipmentCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Equipment> countRoot = countQuery.from(Equipment.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
