package com.example.registration.controllers;

import com.example.registration.model.Housing;
import com.example.registration.service.HousingService;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/owner/")
public class OwnerController {

    private final UserService userService;

    private final HousingService housingService;

    @Autowired
    public OwnerController(UserService userService, HousingService housingService) {
        this.userService = userService;
        this.housingService = housingService;
    }

    //    @PostMapping(path = "addRoom", consumes = {MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<String> createRoom(@RequestBody RoomDto roomDto, @RequestParam("file") MultipartFile file) throws IOException {
//
//        if (roomDto == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File( "-uploaded")));
//                stream.write(bytes);
//                stream.close();
//                return new ResponseEntity<>("You successfully uploaded!", HttpStatus.OK);
//            } catch (Exception e) {
//                return new ResponseEntity<>("You failed to upload", HttpStatus.BAD_REQUEST);
//            }
//        } else {
//            return new ResponseEntity<>("You failed to upload because the file was empty.", HttpStatus.BAD_REQUEST);
//        }

//        Housing room = new Housing();
//        room.setTitle(roomDto.getTitle());
//        room.setNumber(roomDto.getNumber());
//        room.setPrice(roomDto.getPrice());
//        room.setLocation(roomDto.getLocation());
//        room.setDescription(roomDto.getDescription());
//        room.setPreviewImageId(roomDto.getPreviewImageId());
//        room.setImages(roomDto.getImages());
//
//        roomService.save(room, file);
//
//        return new ResponseEntity<>("Room added success!", HttpStatus.OK);
//   }
}
