package com.dsu.industry.domain.reserve.service;

import com.dsu.industry.domain.product.entity.AvailableDate;
import com.dsu.industry.domain.product.exception.AvailableDateNotFoundException;
import com.dsu.industry.domain.product.repository.query.AvailableDateQueryRepository;
import com.dsu.industry.domain.reserve.dto.ReserveDto;
import com.dsu.industry.domain.reserve.dto.ReserveDto.ReserveIdRes;
import com.dsu.industry.domain.reserve.dto.mapper.ReserveMapper;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.exception.ReserveNotFoundException;
import com.dsu.industry.domain.reserve.repository.ReserveRepository;
import com.dsu.industry.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ReserveService {

    private final ReserveRepository reserveRepository;
    private final AvailableDateQueryRepository availableDateQueryRepository;

    public ReserveIdRes reserve(Reserve reserve) {

        // 예약 가능 여부 재확인
        List<AvailableDate> availableDates = availableDateQueryRepository.findByAvailable(
                reserve.getProduct().getId(), reserve.getCheckIn(), reserve.getCheckOut().minusDays(1)
        );

        if(availableDates.size() == 0 || availableDates.size() != ChronoUnit.DAYS.between(
                reserve.getCheckIn(), reserve.getCheckOut())) {
            throw new AvailableDateNotFoundException();
        }

        // 예약 가능 날짜에 대한 수정 벌크 연산 수행
        availableDateQueryRepository.updateByAvailable(
                reserve.getProduct().getId(), reserve.getCheckIn(), reserve.getCheckOut().minusDays(1)
        );

        // 예약 저장
        Reserve reserve_save = reserveRepository.save(reserve);

        return ReserveIdRes.builder()
                .id(reserve_save.getId())
                .build();
    }

//    public ReserveIdRes reserveWithCoupon(ReserveDto.ReserveReq dto) {
//
//        return ReserveIdRes.builder()
//                .id(1L)
//                .build();
//    }

    public ReserveDto.ReserveInfoRes reserve_select(Long reserve_id) {

        Reserve reserve_select = reserveRepository.findById(reserve_id)
                .orElseThrow(() -> new ReserveNotFoundException());

        return ReserveMapper.reserveEntityToDto(reserve_select);
    }

    public List<ReserveDto.ReserveInfoRes> reserve_selectList(User user) {
        List<Reserve> reserves = reserveRepository.findByUser(user)
                .orElseThrow(() -> new ReserveNotFoundException());

        return reserves.stream()
                .map(ReserveMapper::reserveEntityToDto)
                .collect(Collectors.toList());
    }

}
