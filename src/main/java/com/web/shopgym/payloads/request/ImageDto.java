package com.web.shopgym.payloads.request;

import com.web.shopgym.entities.Image;
import com.web.shopgym.enums.EImageType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {

    private String id;
    private String publicId;
    private String url;
    private EImageType type;
    private String secondaryId;

    public static ImageDto toDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .publicId(image.getPublicId())
                .url(image.getUrl())
                .type(image.getType())
                .secondaryId(image.getSecondaryId()).
                build();
    }
}
