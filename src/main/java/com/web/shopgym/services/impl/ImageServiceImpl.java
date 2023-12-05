package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Image;
import com.web.shopgym.repositories.ImageRepository;
import com.web.shopgym.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return this.imageRepository.save(image);
    }

    @Override
    public List<Image> saveAll(List<Image> images) {
        return this.imageRepository.saveAll(images);
    }

    @Override
    public void deleteAll(List<Image> images) {
        this.imageRepository.deleteAll(images);
    }

    @Override
    public List<Image> getAllBySecondaryId(String secondaryId) {
        return this.imageRepository.findAllBySecondaryId(secondaryId);
    }
}
