package edu.bbte.idde.gvim2021.apartmentad.backend.dao.memory;

public enum ApartmentAdMemoryDaoFactory {
    INSTANCE;

    private final ApartmentAdMemoryDao apartmentAdMemoryDao = new ApartmentAdMemoryDao();

    public final ApartmentAdMemoryDao getApartmentAdMemoryDao() {
        return apartmentAdMemoryDao;
    }
}
