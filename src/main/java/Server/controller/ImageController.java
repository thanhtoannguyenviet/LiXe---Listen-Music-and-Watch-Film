package Server.controller;

import Server.model.DAO.APIAccountDAO;
import Server.model.DAO.ImageDAO;
import Server.model.DB.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("Image")
@RestController
public class ImageController {
    @Autowired
    ImageDAO imageDAO;
    APIAccountDAO apiAccountDAO = new APIAccountDAO();
    @RequestMapping(value = "{apiToken}/Post",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> post(@PathVariable("apiToken") String apiToken, @RequestBody ImageEntity entity) {
        if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        imageDAO.save(entity);
        return new ResponseEntity<>("Post Completed", HttpStatus.CREATED);
    }

    @RequestMapping(value = "{apiToken}/Put/{id}",
            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable("apiToken") String apiToken, @RequestBody ImageEntity entity, @PathVariable("id") Long id) {
        if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        if (id == entity.getId()) {
            imageDAO.save(entity);
            return new ResponseEntity<>("Update completed", HttpStatus.OK);
        } else return new ResponseEntity<>("Update Fail", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "{apiToken}/Delete/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("apiToken") String apiToken, @PathVariable("id") Long id) {
        if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
            return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
        }
        if (imageDAO.getByID(id) != null) {
            imageDAO.delete(id);
            return new ResponseEntity<>("Deleted completed", HttpStatus.OK);
        } else return new ResponseEntity<>("Deleted Fail", HttpStatus.BAD_REQUEST);
    }
}
