package edu.bbte.idde.gvim2021.spring.controller;

import edu.bbte.idde.gvim2021.spring.controller.exception.NotFoundException;
import edu.bbte.idde.gvim2021.spring.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.spring.dto.incoming.ApartmentAdCreateDto;
import edu.bbte.idde.gvim2021.spring.dto.outgoing.ApartmentAdDetailsDto;
import edu.bbte.idde.gvim2021.spring.mapper.ApartmentAdMapper;
import edu.bbte.idde.gvim2021.spring.model.ApartmentAd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/apartmentAds")
@Slf4j
public class ApartmentAdController {

    @Autowired
    ApartmentAdDao apartmentAdDao;

    @Autowired
    ApartmentAdMapper apartmentAdMapper;

    @GetMapping
    @ResponseBody
    public Collection<ApartmentAdDetailsDto> findAll() {
        return apartmentAdMapper.modelsToDetailDtos(apartmentAdDao.findAll());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ApartmentAdDetailsDto findById(@PathVariable("id") Long id) {
        log.info("Got get with id {}", id);
        Optional<ApartmentAd> optional = apartmentAdDao.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundException("Apartment with that id does not exist.");
        }

        return apartmentAdMapper.modelToDetailsDto(optional.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ApartmentAdDetailsDto createApartment(@RequestBody @Validated ApartmentAdCreateDto apartmentAdCreateDto) {
        log.info("Received POST with ApartmentAdCreateDao {}", apartmentAdCreateDto);
        ApartmentAd apartmentAd = apartmentAdMapper.createDtoToModel(apartmentAdCreateDto);
        apartmentAd = apartmentAdDao.saveAndFlush(apartmentAd);
        log.info("Apartment ad create successfully {}", apartmentAd);

        return apartmentAdMapper.modelToDetailsDto(apartmentAd);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateApartment(@PathVariable("id") Long id,
                                @RequestBody @Validated ApartmentAdCreateDto apartmentAdCreateDto) {
        ApartmentAd apartmentAd = apartmentAdMapper.createDtoToModel(apartmentAdCreateDto);
        apartmentAdDao.update(id, apartmentAd);
        log.info("Apartment ad with id {} updated", id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteApartment(@PathVariable("id") Long id) {
        apartmentAdDao.deleteById(id);
        log.info("Apartment ad with id {} deleted if it existed", id);
    }
}
