package com.example.whattheeat.repository;

import com.example.whattheeat.dto.ShopDto;
import com.example.whattheeat.entity.ShopEntity;
import com.example.whattheeat.enums.ShopState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {

    //상태가 오픈인 가게만 조회
    List<ShopEntity> findByState(ShopState state);

    //랜덤 조회(전체컬럼)
    //ORDER BY RAND()는 전체 테이블 스캔이 필요해 대용량 데이터에서 매우 느림
    @Query(value = "SELECT * FROM shop WHERE state ='OPEN' ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<ShopEntity> findRandomShops();

    //랜덤 조회(선택 컬럼)
    //ORDER BY RAND()는 전체 테이블 스캔이 필요해 대용량 데이터에서 매우 느림
    @Query("SELECT new com.example.whattheeat.dto.ShopDto(s.name, s.minimumPrice, s.openTime, s.closeTime, CAST(s.state AS string)) FROM ShopEntity s WHERE s.state = 'OPEN' ORDER BY function('RAND') LIMIT 5")
    List<ShopDto> findRandomShopsSelectColum();





}
