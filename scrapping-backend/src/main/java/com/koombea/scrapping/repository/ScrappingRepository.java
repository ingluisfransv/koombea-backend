package com.koombea.scrapping.repository;

import com.koombea.scrapping.model.ScrappedPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrappingRepository extends JpaRepository<ScrappedPage , Long> {
    ScrappedPage findByTitle(String title);
}
