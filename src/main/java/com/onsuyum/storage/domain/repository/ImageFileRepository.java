package com.onsuyum.storage.domain.repository;

import com.onsuyum.storage.domain.model.ImageFile;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageFileRepository extends PagingAndSortingRepository<ImageFile, Long> {

}