package com.dsu.industry.domain.product.repository.query;

import com.dsu.industry.domain.product.entity.Product;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductQueryRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p" +
            " where p.id in (select cd.product.id from AvailableDate cd join cd.product p" +
                            " where cd.date between :checkIn and :checkOut" +
                            " group by p.id having count(p.id) = :count)" +
            " and p.address.addr1_depth_nm = :city" +
            " and p.category.name = :category")
    List<Product> findProductByAvailableWithCategoryAndCity(
            @Param("category") String category,
            @Param("city") String city,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("count") Long count);


    @Query("select p from Product p" +
            " where p = :product" +
            " and :product = (select cd.product from AvailableDate cd join cd.product p" +
                            " where cd.date between :checkIn and :checkOut" +
                            " and cd.product =: product" +
                            " group by p.id having count(p.id) = :count)")
    Optional<Product> findProductByAvailable(
            @Param("product") Product product,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("count") Long count);
}