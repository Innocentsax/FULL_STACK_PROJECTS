package com.decadev.money.way.repository;


import com.decadev.money.way.model.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkRepository extends JpaRepository<Network,Integer> {

    @Query("SELECT n FROM Network n WHERE n.biller_code = ?1")
    List<Network> findByBillerCode(String billerCode);
}
