package edu.bbte.idde.gvim2021.apartmentad.desktop;

import edu.bbte.idde.gvim2021.apartmentad.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.CommentDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.model.ApartmentAd;
import edu.bbte.idde.gvim2021.apartmentad.backend.model.Comment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static final ApartmentAdDao APARTMENT_AD_DATABASE_DAO = AbstractDaoFactory.getApartmentAdDao();
    private static final CommentDao COMMENT_DAO = AbstractDaoFactory.getCommentDao();

    public static void main(String[] args) {
        ApartmentAd apartmentAd = new ApartmentAd();
        apartmentAd.setLocation("Kolozsvaron vhol");
        apartmentAd.setPrice(50000L);
        apartmentAd.setNumberOfBathrooms(3);
        apartmentAd.setNumberOfRooms(1);
        apartmentAd.setDescription("I think there are a bit too much bathrooms.");

        APARTMENT_AD_DATABASE_DAO.create(apartmentAd);
        APARTMENT_AD_DATABASE_DAO.create(apartmentAd);
        APARTMENT_AD_DATABASE_DAO.create(apartmentAd);
        log.info(APARTMENT_AD_DATABASE_DAO.findAll().toString());

        apartmentAd.setNumberOfBathrooms(5);
        APARTMENT_AD_DATABASE_DAO.update(1L, apartmentAd);

        log.info(APARTMENT_AD_DATABASE_DAO.findAll().toString());
        log.info("Apartments with 5 bathrooms: {}", APARTMENT_AD_DATABASE_DAO.findByNumberOfBathrooms(5).toString());

        APARTMENT_AD_DATABASE_DAO.delete(0L);
        log.info(APARTMENT_AD_DATABASE_DAO.findAll().toString());

        Comment comment = new Comment();
        comment.setAuthor("scammer");
        comment.setText("A little big bathroom to room ratio.");
        comment.setApartmentAdId(1L);
        COMMENT_DAO.create(comment);
        log.info(COMMENT_DAO.findAll().toString());
    }
}
