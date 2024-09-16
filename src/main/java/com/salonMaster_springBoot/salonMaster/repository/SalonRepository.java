package com.salonMaster_springBoot.salonMaster.repository;

import com.salonMaster_springBoot.salonMaster.entity.Salon;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface SalonRepository extends JpaRepository<Salon, Long> {
    // Trouve les salons associés à un utilisateur spécifique
    List<Salon> findByUserId(Long userId);
    // Trouve un salon par son identifiant

    Optional<Salon> findById(Long id);

    @Query("SELECT s FROM Salon s WHERE s.user.email = :email")
    Optional<Salon> findByUserEmail(@Param("email") String email);

}
