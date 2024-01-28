package edu.bbte.idde.gvim2021.spring.dao.memory;

import edu.bbte.idde.gvim2021.spring.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.spring.model.ApartmentAd;

import java.util.Collection;
import java.util.stream.Collectors;

public class ApartmentAdMemoryDao extends MemoryDao<ApartmentAd> implements ApartmentAdDao {
    @Override
    public Collection<ApartmentAd> findByNumberOfRooms(Integer numberOfRooms) {
        return entities.values().stream()
                .filter(apartmentAd -> numberOfRooms.equals(apartmentAd.getNumberOfRooms()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ApartmentAd> findByLocation(String location) {
        return entities.values().stream()
                .filter(apartmentAd -> location.equals(apartmentAd.getLocation()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ApartmentAd> findByPrice(Long price) {
        return entities.values().stream()
                .filter(apartmentAd -> price.equals(apartmentAd.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ApartmentAd> findByDescription(String description) {
        return entities.values().stream()
                .filter(apartmentAd -> description.equals(apartmentAd.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ApartmentAd> findByNumberOfBathrooms(Integer numberOfBathrooms) {
        return entities.values().stream()
                .filter(apartmentAd -> numberOfBathrooms.equals(apartmentAd.getNumberOfBathrooms()))
                .collect(Collectors.toList());
    }
}
