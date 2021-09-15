package com.dsu.industry.domain.product.dto;

import com.dsu.industry.domain.product.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PhotoDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhotoIdRes {
        private Long id;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhotoInfoRes {
        private Long id;
        private String img_url;
        private String type;

        public PhotoInfoRes(Photo photo) {
            this.id = photo.getId();
            this.img_url = photo.getPhotoUrl();
            this.type = photo.getPhotoType().toString();
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhotoReviseReq {
        private Long product_id;
        private Long photo_id;
        private String img_url;
        private String type;
    }
}
