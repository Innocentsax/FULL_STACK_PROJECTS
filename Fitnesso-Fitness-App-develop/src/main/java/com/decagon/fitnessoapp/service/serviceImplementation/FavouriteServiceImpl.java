package com.decagon.fitnessoapp.service.serviceImplementation;


import com.decagon.fitnessoapp.dto.ProductResponseDto;
import com.decagon.fitnessoapp.model.user.Favourite;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.repository.FavouriteRepository;
import com.decagon.fitnessoapp.repository.IntangibleProductRepository;
import com.decagon.fitnessoapp.repository.PersonRepository;
import com.decagon.fitnessoapp.repository.TangibleProductRepository;
import com.decagon.fitnessoapp.service.FavouriteService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

public class FavouriteServiceImpl implements FavouriteService {

    private PersonRepository personRepository;
    private TangibleProductRepository tangibleProductRepository;
    private IntangibleProductRepository intangibleProductRepository;
    private FavouriteRepository favouriteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FavouriteServiceImpl(PersonRepository personRepository, FavouriteRepository favouriteRepository, IntangibleProductRepository intangibleProductRepository, TangibleProductRepository tangibleProductRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.favouriteRepository = favouriteRepository;
        this.intangibleProductRepository = intangibleProductRepository;
        this.tangibleProductRepository = tangibleProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean handleFavourite(String username, Long productId) {

        Person person = personRepository.findPersonByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found in favourite Service Implementation"));

        Favourite favourite = new Favourite();

        favourite.setProductId(productId);

        boolean existsFavourite = favouriteRepository.existsFavouriteByPersonAndProductId(person, productId);

        if(existsFavourite){
            favouriteRepository.deleteFavouriteByPersonAndProductId(person, productId);
            return false;
        }
        else{
            favourite.setPerson(person);
            favouriteRepository.save(favourite);
        }

        return true;
    }

    @Override
    public Boolean checkFaveDefault(String username, Long productId) {
        Person person = personRepository.findPersonByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found in favourite Service Implementation"));

        Favourite favourite = new Favourite();

        favourite.setProductId(productId);


        boolean existsFavourite = favouriteRepository.existsFavouriteByPersonAndProductId(person, productId);
        return existsFavourite;
    }

    @Override
    public ResponseEntity<String> addOrDeleteFavourite(Long productId, Authentication authentication) {

        Person person = personRepository.findPersonByUserName(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User not found in favourite Service Implementation"));

        Favourite favourite = new Favourite();

        favourite.setProductId(productId);


        boolean existsFavourite = favouriteRepository.existsFavouriteByPersonAndProductId(person, productId);

        if(existsFavourite){
            favouriteRepository.deleteFavouriteByPersonAndProductId(person, productId);
            return ResponseEntity.ok().body("Item successfully deleted from favourite");
        }
        else{
            favourite.setPerson(person);
            favouriteRepository.save(favourite);
        }


        return ResponseEntity.ok().body("Item successfully added to favourite");
    }

    @Override
    public List<ProductResponseDto> viewFavourites(Authentication authentication) {

//        ProductResponseDto productResponseDto = new ProductResponseDto();
        List<ProductResponseDto> responseDtos = new ArrayList<>();

        Person person1 = personRepository.findPersonByUserName(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found in favourite Service Implementation"));

        List<Favourite> favouriteResponseList = favouriteRepository.findFavouritesByPerson(person1);

        for (Favourite perFavourite : favouriteResponseList) {

            final Long productId = perFavourite.getProductId();

            ProductResponseDto productResponseDto = modelMapper.map(tangibleProductRepository.findById(perFavourite.getProductId()).get(), ProductResponseDto.class);

            if(productResponseDto != null){
                productResponseDto.setProductType("PRODUCT");
                responseDtos.add(productResponseDto);
            }
            else{

                ProductResponseDto productResponseDto1 = modelMapper.map(intangibleProductRepository.findById(perFavourite.getProductId()).get(), ProductResponseDto.class);
                productResponseDto.setProductType("SERVICE");
                responseDtos.add(productResponseDto1);}
        }
        return responseDtos;
    }
}
