package com.dsu.industry.domain.product.service;

import com.dsu.industry.domain.product.dto.PhotoDto;
import com.dsu.industry.domain.product.entity.Photo;
import com.dsu.industry.domain.product.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PhotoService {

    public final PhotoRepository photoRepository;

    public PhotoDto.PhotoIdRes photo_save(Photo photo) {
        Photo photo_save = photoRepository.save(photo);

        return PhotoDto.PhotoIdRes.builder()
                .id(photo_save.getId())
                .build();
    }
}
