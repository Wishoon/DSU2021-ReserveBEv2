package com.dsu.industry.domain.product.repository.query;

import com.dsu.industry.domain.product.entity.AvailableDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AvailableDateQueryRepository {

    private final EntityManager em;

    // 한 상품의 체크인 체크아웃 날짜의 예약 가능 여부 확인
    public List<AvailableDate> findByAvailable(Long product_id, LocalDate checkIn, LocalDate checkOut) {

        return em.createQuery(
                        "select a from AvailableDate a" +
                                " where a.product.id = :id" +
                                " and a.isTrue = true" +
                                " and a.date between :checkIn and :checkOut", AvailableDate.class)
                .setParameter("id", product_id)
                .setParameter("checkIn", checkIn)
                .setParameter("checkOut", checkOut)
                .getResultList();
    }

    public void updateByAvailable(Long product_id, LocalDate checkIn, LocalDate checkOut) {
        em.createQuery(
                        "update AvailableDate a" +
                                " set a.isTrue = false" +
                                " where a.product.id = :id" +
                                " and a.date between :checkIn and :checkOut")
                .setParameter("id", product_id)
                .setParameter("checkIn", checkIn)
                .setParameter("checkOut", checkOut)
                .executeUpdate();

        em.clear();
    }
}
