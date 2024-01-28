package edu.bbte.idde.gvim2021.spring.dao;

import edu.bbte.idde.gvim2021.spring.model.ApartmentAd;

import java.util.Collection;

public interface ApartmentAdDao extends Dao<ApartmentAd> {
    Collection<ApartmentAd> findByNumberOfRooms(Integer numberOfRooms);

    Collection<ApartmentAd> findByLocation(String location);

    Collection<ApartmentAd> findByPrice(Long price);

    Collection<ApartmentAd> findByDescription(String description);

    Collection<ApartmentAd> findByNumberOfBathrooms(Integer numberOfBathrooms);
}
