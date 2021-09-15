package com.dsu.industry.domain.product.repository.query;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {

    private final EntityManager em;

    // 아직 동적 쿼리 적용이 되어 있지 않음. -> QueryDSL 변경 예정
    // distinct를 할 경우 1:N 구조의 중복 데이터를 제거해주기는 함
    // 단, DB쿼리에서는 동일하게 나가기 떼문에 JPA 내에서 자체적으로 distinct를 해준다. (페이징 불가능)
    public List<Product> findProductByAvailableDateWithCategoryAndCity(
            ProductDto.ProductSearchReq dto) {

        return em.createQuery(
                "select p from Product p" +
                        " join fetch p.category c" +
                        " join fetch p.address a" +
                        " where p.id in (select cd.product.id from AvailableDate cd join cd.product p" +
                                        " where cd.date between :checkIn and :checkOut" +
                                        " group by p.id having count(p.id) = :dateCnt)" +
                        " and a.addr1_depth_nm = :city" +
                        " and c.name = :category", Product.class)
                .setParameter("category", dto.getCategory())
                .setParameter("city", dto.getCity())
                .setParameter("checkIn", dto.getCheckIn())
                .setParameter("checkOut", dto.getCheckOut())
                .setParameter("dateCnt", dto.getDateCnt())
                .getResultList();
    }


}
