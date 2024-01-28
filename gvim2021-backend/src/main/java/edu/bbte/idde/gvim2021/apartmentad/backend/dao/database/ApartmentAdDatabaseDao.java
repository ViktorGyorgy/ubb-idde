package edu.bbte.idde.gvim2021.apartmentad.backend.dao.database;

import edu.bbte.idde.gvim2021.apartmentad.backend.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.model.ApartmentAd;

import java.util.Collection;

public class ApartmentAdDatabaseDao extends DatabaseDao<ApartmentAd> implements ApartmentAdDao {

    public ApartmentAdDatabaseDao() {
        super("APARTMENTADS", ApartmentAd.class);
    }

    @Override
    public Collection<ApartmentAd> findByNumberOfRooms(Integer numberOfRooms) {
        return executeQuery("numberOfRooms = " + numberOfRooms);
    }

    @Override
    public Collection<ApartmentAd> findByLocation(String location) {
        return executeQuery("location = " + location);
    }

    @Override
    public Collection<ApartmentAd> findByPrice(Long price) {
        return executeQuery("price = " + price);
    }

    @Override
    public Collection<ApartmentAd> findByDescription(String description) {
        return executeQuery("description = " + description);
    }

    @Override
    public Collection<ApartmentAd> findByNumberOfBathrooms(Integer numberOfBathrooms) {
        return executeQuery("numberOfBathrooms = " + numberOfBathrooms);
    }
}
