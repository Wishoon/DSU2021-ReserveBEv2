package com.dsu.industry.domain.product.repository.query;

import com.dsu.industry.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductQueryRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p" +
            " where p.id in (select cd.product.id from AvailableDate cd join cd.product p where cd.date between :checkIn and :checkOut group by p.id having count(p.id) = :count)" +
            " and p.address.addr1_depth_nm = :city" +
            " and p.category.name = :category")
    List<Product> findProductByAvailable(@Param("category") String category,
                                          @Param("city") String city,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut,
                                          @Param("count") Long count);
}