package com.dsu.industry;

import com.dsu.industry.domain.product.entity.*;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.entity.ReserveState;
import com.dsu.industry.global.common.Address;
import com.dsu.industry.domain.user.entity.AuthProvider;
import com.dsu.industry.domain.user.entity.Authority;
import com.dsu.industry.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {

            Category category = new Category();
            category.setName("호텔");
            em.persist(category);

            Address address = new Address("부산", "강서구", "명지동", "상세주소");

            Product product1 = new Product();
            product1.setName("상품1");
            product1.setSub_name("상품 부 이름1");
            product1.setPrice(20000L);
            product1.setPeople_maxCnt(10L);
            product1.setAddress(address);
            product1.setCategory(category);
            product1.setDescription("상품에 대한 설명");

            Photo photo1 = new Photo();
            photo1.setProduct(product1);
            photo1.setPhotoType(PhotoType.MAIN);
            photo1.setPhotoUrl("https://dsu-reserve-img.s3.ap-northeast-2.amazonaws.com/static/img1.jpeg");
            product1.getPhotoList().add(photo1);

            AvailableDate date1 = new AvailableDate();
            date1.setProduct(product1);
            date1.setDate(LocalDate.now());
            product1.getAvailableDateList().add(date1);

            em.persist(product1);

            Product product2 = new Product();
            product2.setName("상품2");
            product2.setSub_name("상품 부 이름2");
            product2.setPrice(10000L);
            product2.setPeople_maxCnt(10L);
            product2.setAddress(address);
            product2.setCategory(category);
            product2.setDescription("상품에 대한 설명2");

            Photo photo2 = new Photo();
            photo2.setProduct(product2);
            photo2.setPhotoType(PhotoType.MAIN);
            photo2.setPhotoUrl("https://dsu-reserve-img.s3.ap-northeast-2.amazonaws.com/static/img1.jpeg");
            product2.getPhotoList().add(photo2);

            AvailableDate date2 = new AvailableDate();
            date2.setProduct(product2);
            date2.setDate(LocalDate.now().plusDays(1));
            date2.setTrue(false);
            product2.getAvailableDateList().add(date2);

            AvailableDate date3 = new AvailableDate();
            date3.setProduct(product2);
            date3.setDate(LocalDate.now().plusDays(2));
            product2.getAvailableDateList().add(date3);

            em.persist(product2);

            User user = new User();
            user.setName("유저1");
            user.setEmail("test1@gmail.com");
            user.setPassword("$2a$10$hpueWKSfF8aDcPRlJVDGfuwpUcU9lgHYD9R4cwnjYtFad.sds5pm.");
            user.setPhone("01012345678");
            user.setAddress(address);
            user.setAuthority(Authority.USER);
            user.setAuthProvider(AuthProvider.local);

            em.persist(user);

            Reserve reserve = new Reserve();
            reserve.setProduct(product2);
            reserve.setUser(user);
            reserve.setPeople_reserveCnt(4L);
            reserve.setTotal_price(10000L);
            reserve.setSales_price(4000L);
            reserve.setResult_price(6000L);
            reserve.setReserveState(ReserveState.CHECKIN_BEFORE);
            reserve.setCheckIn(LocalDate.now().plusDays(1));
            reserve.setCheckOut(LocalDate.now().plusDays(2));

            em.persist(reserve);
        }
    }
}
