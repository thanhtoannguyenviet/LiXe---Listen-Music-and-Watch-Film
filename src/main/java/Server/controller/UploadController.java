package Server.controller;

import Server.model.DAO.UploadDAO;
import Server.model.DB.UploadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("File")
@RestController
public class UploadController {
    @Autowired
    UploadDAO uploadDAO;
    @RequestMapping(value = "/Post",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> postImage(@RequestBody UploadEntity entity){
        uploadDAO.save(entity);
        return new ResponseEntity<>("Post Completed", HttpStatus.CREATED);
    }
    @RequestMapping(value = "/Put/{id}",
            method = RequestMethod.PUT)
    @ResponseBody
    public  ResponseEntity<?> updateAccount(@RequestBody UploadEntity entity, @PathVariable("id") Long id ){
        if(uploadDAO.getByID(id)!=null)
            uploadDAO.save(entity);
        return new ResponseEntity<>("Update Completed",HttpStatus.OK);
    }
    @RequestMapping(value = "/Delete/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id){
        uploadDAO.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/Get/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id){

        return new ResponseEntity<>( uploadDAO.getByID(id),HttpStatus.OK);
    }
}
