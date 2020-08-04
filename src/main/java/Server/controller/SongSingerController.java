package Server.controller;


import Server.model.DAO.APIAccountDAO;
import Server.model.DAO.SongSingerDAO;
import Server.model.DB.SongCategorysongEntity;
import Server.model.DB.SongSingerEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/MusicSite/SongSinger")
@RestController
public class SongSingerController {
    SongSingerDAO songSingerDAO = new SongSingerDAO();
    APIAccountDAO apiAccountDAO = new APIAccountDAO();

    @RequestMapping(value = "/Post/",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> registerActor(@RequestBody SongSingerEntity songSingerEntity) {
//            if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
//                return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
//            }
        songSingerEntity=  songSingerDAO.save(songSingerEntity);
        return new ResponseEntity<>(songSingerEntity, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Delete/{idSong}/{idSinger}",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public ResponseEntity<?> deleteActor(@PathVariable("idSong") Long idSong, @PathVariable("idSinger") Long idSinger) {
//            if (apiToken == null || apiToken.isEmpty() || apiAccountDAO.checkToken(apiToken) == 0) {
//                return new ResponseEntity<>("Token is not valid.", HttpStatus.FORBIDDEN);
//            }
        if (songSingerDAO.getId(idSong,idSinger) != null) {
            for (SongSingerEntity item : songSingerDAO.getId(idSong,idSinger)) {
                Long id = item.getId();
                songSingerDAO.delete(id);
            }
            return new ResponseEntity<>("Delete Completed", HttpStatus.OK);
        } else return new ResponseEntity<>("Delte Fail", HttpStatus.BAD_REQUEST);
    }
}