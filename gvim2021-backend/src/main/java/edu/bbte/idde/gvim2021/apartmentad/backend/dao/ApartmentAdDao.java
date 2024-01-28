package edu.bbte.idde.gvim2021.apartmentad.backend.dao;

import edu.bbte.idde.gvim2021.apartmentad.backend.model.ApartmentAd;

import java.util.Collection;

public interface ApartmentAdDao extends Dao<ApartmentAd> {
    Collection<ApartmentAd> findByNumberOfRooms(Integer numberOfRooms);

    Collection<ApartmentAd> findByLocation(String location);

    Collection<ApartmentAd> findByPrice(Long price);

    Collection<ApartmentAd> findByDescription(String description);

    Collection<ApartmentAd> findByNumberOfBathrooms(Integer numberOfBathrooms);
}
