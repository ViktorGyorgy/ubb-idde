package edu.bbte.idde.gvim2021.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.model.ApartmentAd;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/apartmentAds")
@Slf4j
public class ApartmentAdServlet extends HttpServlet {
    private static final ApartmentAdDao APARTMENT_AD_DAO =
            AbstractDaoFactory.getApartmentAdDao();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("GET /apartmentAds");

        resp.setHeader("Content-type", "application/json");

        //if id != null, return apartment with id
        String id = req.getParameter("id");
        log.debug("THE ID IS {}", id);

        if (id != null) {
            Long idAsLong;
            try {
                idAsLong = Long.parseLong(id);
            } catch (NumberFormatException e) {
                resp.sendError(400, "Type of Id is not long.");
                return;
            }

            ApartmentAd apartmentAd = APARTMENT_AD_DAO.findById(idAsLong);

            if (apartmentAd == null) {
                resp.sendError(404, "Apartment with id not found.");
                return;
            }

            objectMapper.writeValue(resp.getWriter(), apartmentAd);
            return;
        }

        String location = req.getParameter("location");
        if (location != null) {
            objectMapper.writeValue(resp.getWriter(), APARTMENT_AD_DAO.findByLocation(location));
            return;
        }

        objectMapper.writeValue(resp.getWriter(), APARTMENT_AD_DAO.findAll());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("POST /apartmentAds");
        ApartmentAd apartmentAd;

        try {
            apartmentAd = objectMapper.readValue(req.getInputStream(), ApartmentAd.class);
        } catch (UnrecognizedPropertyException | JsonParseException | InvalidFormatException e) {
            log.error("Invalid apartmentAd json.");
            resp.sendError(400, "Invalid apartmentAd json.");
            return;
        }

        if (isNullFieldInApartmentAd(apartmentAd)) {
            log.error("Invalid apartmentAd has null field.");
            resp.sendError(400, "apartmentAd has null field.");
            return;
        }

        log.info("received apartmentAd {}", apartmentAd);
        APARTMENT_AD_DAO.create(apartmentAd);

        resp.getWriter().write("Apartment added successfully");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("POST /apartmentAds");

        Long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Id is not present/not valid.");
            return;
        }

        ApartmentAd apartmentAd;
        try {
            apartmentAd = objectMapper.readValue(req.getInputStream(), ApartmentAd.class);
        } catch (UnrecognizedPropertyException | JsonParseException | InvalidFormatException e) {
            log.error("Invalid apartmentAd json.");
            resp.sendError(400, "Invalid apartmentAd json.");
            return;
        }

        if (isNullFieldInApartmentAd(apartmentAd)) {
            log.error("Invalid apartmentAd has null field.");
            resp.sendError(400, "apartmentAd has null field.");
            return;
        }

        if (APARTMENT_AD_DAO.findById(id) == null) {
            resp.sendError(404, "Apartment with such id is not present.");
            return;
        }

        log.info("Patched apartmentAd with id {} to {}", id, apartmentAd);
        APARTMENT_AD_DAO.update(id, apartmentAd);

        resp.getWriter().write("Succesfully changed the apartmentAd");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("DELETE /apartmentAds");
        Long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Id is not present/not valid.");
            return;
        }

        APARTMENT_AD_DAO.delete(id);
        resp.getWriter().write("ApartmentAd deleted if it was present");
    }

    private boolean isNullFieldInApartmentAd(ApartmentAd apartmentAd) {
        return apartmentAd.getNumberOfRooms() == null
                || apartmentAd.getLocation() == null
                || apartmentAd.getPrice() == null
                || apartmentAd.getDescription() == null
                || apartmentAd.getNumberOfBathrooms() == null;
    }
}
