package com.web.shopgym.services;

import com.web.shopgym.entities.Image;
import com.web.shopgym.enums.EImageType;

import java.util.List;

public interface ImageService {

    Image save(Image image);

    List<Image> saveAll(List<Image> images);

    void deleteAll(List<Image> images);

    List<Image> getAllBySecondaryId(String secondaryId);

    List<Image> findAllBySecondaryIdAndType(String secondaryId, EImageType type);
}
