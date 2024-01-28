package edu.bbte.idde.gvim2021.apartmentad.backend.dao;

import edu.bbte.idde.gvim2021.apartmentad.backend.dao.database.ApartmentAdDatabaseDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.database.CommentDatabaseDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.memory.ApartmentAdMemoryDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.memory.CommentMemoryDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AbstractDaoFactory {
    private static final ApartmentAdDao AP_AD_DAO;
    private static final CommentDao COM_DAO;

    static {
        if ("prod".equals(System.getenv("profile"))) {
            AP_AD_DAO = new ApartmentAdDatabaseDao();
            COM_DAO = new CommentDatabaseDao();
        } else {
            AP_AD_DAO = new ApartmentAdMemoryDao();
            COM_DAO = new CommentMemoryDao();
        }
    }

    private AbstractDaoFactory() {
    }

    public static ApartmentAdDao getApartmentAdDao() {
        return AP_AD_DAO;
    }

    public static CommentDao getCommentDao() {
        return COM_DAO;
    }
}
