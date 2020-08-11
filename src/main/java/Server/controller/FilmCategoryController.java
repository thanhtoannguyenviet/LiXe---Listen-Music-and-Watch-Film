package Server.controller;

import Server.model.DAO.APIAccountDAO;
import Server.model.DAO.FilmCategoryFilmDAO;
import Server.model.DB.FilmActorEntity;
import Server.model.DB.FilmCategoryfilmEntity;
import Server.model.DTO.FilmCategoryfilmInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("FilmSite/FilmCategory")
@RestController
public class FilmCategoryController {
    @Autowired
    FilmCategoryFilmDAO filmCategoryFilmDAO;
    APIAccountDAO apiAccountDAO = new APIAccountDAO();

    @RequestMapping(value = "/Post",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody FilmCategoryfilmInDTO filmCategoryfilmInDTO) {
        if (filmCategoryfilmInDTO == null || filmCategoryfilmInDTO.getApiToken() == null
                || filmCategoryfilmInDTO.getApiToken().isEmpty() || apiAccountDAO.checkToken(filmCategoryfilmInDTO.getApiToken()) != 1) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        FilmCategoryfilmEntity entity = filmCategoryFilmDAO.save(filmCategoryfilmInDTO.getFilmCategoryfilmEntity());
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Put/{id}",
            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody FilmCategoryfilmInDTO filmCategoryfilmInDTO, @PathVariable("id") Long id) {
        if (filmCategoryfilmInDTO == null || filmCategoryfilmInDTO.getApiToken() == null
                || filmCategoryfilmInDTO.getApiToken().isEmpty() || apiAccountDAO.checkToken(filmCategoryfilmInDTO.getApiToken()) != 1) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        if (filmCategoryFilmDAO.getByID(id) != null) {
            filmCategoryFilmDAO.save(filmCategoryfilmInDTO.getFilmCategoryfilmEntity());
            return new ResponseEntity<>("Update Completed", HttpStatus.OK);
        } else return new ResponseEntity<>("Update Fail", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/Delete/{idFilm}/{idCategory}",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public ResponseEntity<?> delete(@RequestBody String apiToken, @PathVariable("idFilm") Long idFilm, @PathVariable("idCategory") Long idCategory) {
        if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) != 1) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        if (filmCategoryFilmDAO.getId(idFilm, idCategory) != null) {
            for (FilmCategoryfilmEntity item : filmCategoryFilmDAO.getId(idFilm, idCategory)) {
                Long id = item.getId();
                filmCategoryFilmDAO.delete(id);
            }
            return new ResponseEntity<>("Delete Completed", HttpStatus.OK);
        } else return new ResponseEntity<>("Delte Fail", HttpStatus.BAD_REQUEST);
    }

}
