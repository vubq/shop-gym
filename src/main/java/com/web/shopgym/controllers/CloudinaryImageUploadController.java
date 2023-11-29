package com.web.shopgym.controllers;

import com.web.shopgym.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/cloudinary")
public class CloudinaryImageUploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<Map> uploadImage(@RequestParam("image") MultipartFile file){
        Map data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<Map> deleteByPublicId(@PathVariable("id") String id){
        Map data = this.cloudinaryService.deleteByPublicId(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
