package com.salonMaster_springBoot.salonMaster.repository;

import com.salonMaster_springBoot.salonMaster.entity.ChiffreAffaires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaRepository extends JpaRepository<ChiffreAffaires, Long> {

    List<ChiffreAffaires>findBySalonId(Long salonId);
    List<ChiffreAffaires>findBySalonIdAndMois(Long salonId, String mois);

    @Query(value = "SELECT AVG(ca) FROM chiffresaffaires", nativeQuery = true)
    double findAvgCaForFrance();

    @Query(value = "SELECT AVG(ca) FROM chiffresaffaires c JOIN salons s ON c.salon_id = s.id WHERE s.region = :region", nativeQuery = true)
    double findAvgCaForRegion(@Param("region") String region);

    @Query(value = "SELECT AVG(ca) FROM chiffresaffaires c JOIN salons s ON c.salon_id = s.id WHERE s.departement = :departement", nativeQuery = true)
    double findAvgCaForDepartement(@Param("departement") String departement);

    @Query(value = "Select AVG(ca) FROM chiffresaffaires c where c.salon_id = :salonId",nativeQuery = true )
    Double findAvgCaForUser(@Param("salonId") Long salonId);

    @Modifying
    @Query(value = "DELETE FROM chiffresaffaires WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);


}
