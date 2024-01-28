package edu.bbte.idde.gvim2021.spring.controller;

import edu.bbte.idde.gvim2021.spring.controller.exception.NotFoundException;
import edu.bbte.idde.gvim2021.spring.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.spring.dto.incoming.CommentCreateDto;
import edu.bbte.idde.gvim2021.spring.dto.outgoing.CommentDetailsDto;
import edu.bbte.idde.gvim2021.spring.mapper.CommentMapper;
import edu.bbte.idde.gvim2021.spring.model.ApartmentAd;
import edu.bbte.idde.gvim2021.spring.model.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/apartmentAds/{aptId}/comments")
@Slf4j
@Profile("jpa")
public class CoupledController {

    @Autowired
    ApartmentAdDao apartmentAdDao;

    @Autowired
    CommentMapper commentMapper;

    @GetMapping()
    @ResponseBody
    public Collection<CommentDetailsDto> getComments(@PathVariable("aptId") Long aptId) {
        Optional<ApartmentAd> optional = apartmentAdDao.findById(aptId);
        if (optional.isEmpty()) {
            throw new NotFoundException("ApartmentAd with id = " + aptId + " not found.");
        }

        ApartmentAd apartmentAd = optional.get();
        log.info("Get comments of apartmentAd with id = {}", aptId);
        return commentMapper.modelsToDetailDtos(apartmentAd.getComments());
    }

    @DeleteMapping()
    public void deleteComments(@PathVariable("aptId") Long aptId) {
        Optional<ApartmentAd> optional = apartmentAdDao.findById(aptId);
        if (optional.isEmpty()) {
            throw new NotFoundException("ApartmentAd with id = " + aptId + " not found.");
        }

        ApartmentAd apartmentAd = optional.get();
        apartmentAd.getComments().clear();
        apartmentAdDao.saveAndFlush(apartmentAd);

        log.info("Deleted comments of apartmentAd id = {}", aptId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CommentDetailsDto createComment(@PathVariable("aptId") Long aptId,
                                           @RequestBody @Validated CommentCreateDto commentCreateDto) {
        Optional<ApartmentAd> optional = apartmentAdDao.findById(aptId);
        if (optional.isEmpty()) {
            throw new NotFoundException("ApartmentAd with id = " + aptId + " not found.");
        }

        ApartmentAd apartmentAd = optional.get();

        Comment comment = commentMapper.createDtoToModel(commentCreateDto);
        comment.setApartmentAd(apartmentAd);
        apartmentAd.getComments().add(comment);
        apartmentAdDao.saveAndFlush(apartmentAd);
        comment = apartmentAd.getComments().get(apartmentAd.getComments().size() - 1);

        return commentMapper.modelToDetailsDto(comment);
    }
}
