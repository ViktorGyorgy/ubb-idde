package edu.bbte.idde.gvim2021.spring.dao.jpa;

import edu.bbte.idde.gvim2021.spring.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.spring.model.ApartmentAd;
import edu.bbte.idde.gvim2021.spring.model.BaseEntity;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface ApartmentAdJpaDao extends JpaRepository<ApartmentAd, BaseEntity>, ApartmentAdDao {
    @Override
    void deleteById(Long entityId);

    @Override
    @Modifying()
    @Transactional
    @Query("update ApartmentAd set numberOfRooms = :#{#myAp.numberOfRooms}, "
            + "numberOfBathrooms = :#{#myAp.numberOfBathrooms}, "
            + "location = :#{#myAp.location}, "
            + "price = :#{#myAp.price}, "
            + "description = :#{#myAp.description} "
            + "where id = :id")
    void update(@Param("id") Long id, @Param("myAp") ApartmentAd apartmentAd);

}
