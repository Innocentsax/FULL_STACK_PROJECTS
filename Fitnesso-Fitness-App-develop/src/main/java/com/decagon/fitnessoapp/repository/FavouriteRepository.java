package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.dto.FavouriteResponse;
import com.decagon.fitnessoapp.model.user.Favourite;
import com.decagon.fitnessoapp.model.user.Person;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    Optional<Favourite> findFavouriteByProductId(Long id);
    List<Favourite> findFavouritesByPerson(@NonNull Person person);
    @Transactional
    void deleteFavouriteByPersonAndProductId(@NonNull Person person, @NonNull Long productId);

    boolean existsFavouriteByPersonAndProductId(@NonNull Person person, @NonNull Long productId);

}
