package Server.controller;

import Server.model.DAO.*;
import Server.model.DB.*;
import Server.model.DTO.CategoryFilmDTO;
import Server.model.DTO.CategoryfilmInDTO;
import Server.model.DTO.Criteria;
import Server.model.DTO.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/FilmSite/Category")
@RestController
public class CategoryFilmController {
    @Autowired
    CategoryFilmDAO categoryFilmDAO;
    FilmSiteDAO filmSiteDAO = new FilmSiteDAO();
    FilmCategoryFilmDAO filmCategoryFilmDAO = new FilmCategoryFilmDAO();
    APIAccountDAO apiAccountDAO = new APIAccountDAO();

    @RequestMapping(value = "/Post",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody CategoryfilmInDTO categoryfilmInDTO) {
        if (categoryfilmInDTO == null || categoryfilmInDTO.getApiToken() == null
                || categoryfilmInDTO.getApiToken().isEmpty() || apiAccountDAO.checkToken(categoryfilmInDTO.getApiToken()) == 0) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        CategoryfilmEntity categorySongEntity = categoryFilmDAO.save(categoryfilmInDTO.getCategoryfilmEntity());
        return new ResponseEntity<>(categorySongEntity, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Put/{id}",
            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody CategoryfilmInDTO categoryfilmInDTO, @PathVariable("id") Long id) {
        if (categoryfilmInDTO == null || categoryfilmInDTO.getApiToken() == null
                || categoryfilmInDTO.getApiToken().isEmpty() || apiAccountDAO.checkToken(categoryfilmInDTO.getApiToken()) == 0) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        if (id == categoryfilmInDTO.getCategoryfilmEntity().getId()) {
            categoryFilmDAO.save(categoryfilmInDTO.getCategoryfilmEntity());
            return new ResponseEntity<>("Update Completed", HttpStatus.OK);
        } else return new ResponseEntity<>("Update Fail", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/Delete/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public ResponseEntity<?> delete(@RequestBody String apiToken, @PathVariable("id") Long id) {
        if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        if (categoryFilmDAO.getByID(id) != null) {
            categoryFilmDAO.delete(id);
            return new ResponseEntity<>("Delete Completed", HttpStatus.OK);
        } else return new ResponseEntity<>("Delte Fail", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/GetDetail/{id}",
            method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ResponseEntity<?> getDetail(@RequestBody String apiToken, @PathVariable("id") Long id) {
        if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        CategoryfilmEntity categoryfilmEntity = categoryFilmDAO.getByID(id);
        if (categoryfilmEntity != null) {
            CategoryFilmDTO categoryFilmDTO = getCategoryFilmDTO(categoryfilmEntity);
            return new ResponseEntity<>(categoryFilmDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Found", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/GetAll/",
            method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ResponseEntity<?> getAll(@RequestBody String apiToken) {
        try {
            if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
                return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
            }
            List<CategoryfilmEntity> categoryfilmEntityList = categoryFilmDAO.getAll();
            if (!categoryfilmEntityList.isEmpty()) {
                List<CategoryFilmDTO> categoryFilmDTOList = new ArrayList<>();
                for (CategoryfilmEntity item : categoryfilmEntityList
                ) {
                    CategoryFilmDTO categoryFilmDTO = getCategoryFilmDTO(item);
                    if (categoryFilmDTO != null)
                        categoryFilmDTOList.add(categoryFilmDTO);
                }
                return new ResponseEntity<>(categoryFilmDTOList, HttpStatus.OK);
            }
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LogEntity log = new LogEntity(e);
            (new LogDAO()).save(log);
            e.printStackTrace();
            return new ResponseEntity<>("If you are admin, Check table Log to see ErrorMsg", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/GetAllHasPage{itemOnPage}/{page}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getPage(@RequestBody String apiToken, @PathVariable("page") int page, @PathVariable("itemOnPage") int itemOnPage) {
        try {
            if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
                return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
            }
            Criteria criteria = new Criteria();
            criteria.setClazz(CategoryfilmEntity.class);
            criteria.setCurrentPage(page);
            criteria.setItemPerPage(itemOnPage);
            List<CategoryfilmEntity> categoryfilmEntityList = categoryFilmDAO.loadDataPagination(criteria);
            if (!categoryfilmEntityList.isEmpty()) {
                List<CategoryFilmDTO> categoryFilmDTOList = new ArrayList<>();
                for (CategoryfilmEntity item : categoryfilmEntityList) {
                    CategoryFilmDTO categoryFilmDTO = getCategoryFilmDTO(item);
                    if (categoryFilmDTO != null)
                        categoryFilmDTOList.add(categoryFilmDTO);
                }
                return new ResponseEntity<>(categoryFilmDTOList, HttpStatus.OK);
            }
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LogEntity log = new LogEntity(e);
            (new LogDAO()).save(log);
            e.printStackTrace();
            return new ResponseEntity<>("If you are admin, Check table Log to see ErrorMsg", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/GetTop{itop}/", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getTop(@RequestBody String apiToken, @PathVariable("itop") int itop) {
        try {
            if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
                return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
            }
            Criteria criteria = new Criteria();
            criteria.setClazz(CategoryfilmEntity.class);
            criteria.setTop(itop);
            List<CategoryfilmEntity> categoryfilmEntityList = categoryFilmDAO.getTop10(criteria);
            if (!categoryfilmEntityList.isEmpty()) {
                List<CategoryFilmDTO> categoryFilmDTOList = new ArrayList<>();
                for (CategoryfilmEntity item : categoryfilmEntityList
                ) {
                    CategoryFilmDTO categoryFilmDTO = getCategoryFilmDTO(item);
                    if (categoryFilmDTO != null)
                        categoryFilmDTOList.add(categoryFilmDTO);
                }
                return new ResponseEntity<>(categoryFilmDTOList, HttpStatus.OK);
            }
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LogEntity log = new LogEntity(e);
            (new LogDAO()).save(log);
            e.printStackTrace();
            return new ResponseEntity<>("If you are admin, Check table Log to see ErrorMsg", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/GetRandom{iRandom}/",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ResponseEntity<?> getRandom(@RequestBody String apiToken, @PathVariable("iRandom") int iRandom) {
        try {
            if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
                return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
            }
            Criteria criteria = new Criteria();
            criteria.setTop(iRandom);
            criteria.setClazz(CategoryfilmEntity.class);
            List<CategoryfilmEntity> categoryfilmEntityList = categoryFilmDAO.getTopRandom(criteria);
            if (!categoryfilmEntityList.isEmpty()) {
                List<CategoryFilmDTO> categoryFilmDTOList = new ArrayList<>();
                for (CategoryfilmEntity item : categoryfilmEntityList
                ) {
                    CategoryFilmDTO categoryFilmDTO = getCategoryFilmDTO(item);
                    if (categoryFilmDTO != null)
                        categoryFilmDTOList.add(categoryFilmDTO);
                }
                return new ResponseEntity<>(categoryFilmDTOList, HttpStatus.OK);
            }
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LogEntity log = new LogEntity(e);
            (new LogDAO()).save(log);
            e.printStackTrace();
            return new ResponseEntity<>("If you are admin, Check table Log to see ErrorMsg", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/Count", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> count(@RequestBody String apiToken) {
        try {
            if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
                return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(categoryFilmDAO.count(), HttpStatus.OK);
        } catch (Exception e) {
            new LogDAO().save(new LogEntity(e));
            e.printStackTrace();
            return new ResponseEntity<>("If you are admin, Check table Log to see ErrorMsg", HttpStatus.BAD_REQUEST);
        }
    }

    private CategoryFilmDTO getCategoryFilmDTO(CategoryfilmEntity categoryfilmEntity) {
        List<FilmCategoryfilmEntity> filmCategoryfilmEntities = filmCategoryFilmDAO.getId("categoryid", categoryfilmEntity.getId() + "");
        List<FilmDTO> filmDTOList = new ArrayList<>();
        if (!filmCategoryfilmEntities.isEmpty()) {
            for (FilmCategoryfilmEntity item : filmCategoryfilmEntities
            ) {
                FilmDTO filmDTO = filmSiteDAO.getFilmDTOById(item.getFilmid());
                if (filmDTO != null)
                    filmDTOList.add(filmDTO);
            }
        }
        return new CategoryFilmDTO(categoryfilmEntity, filmDTOList);
    }
}
