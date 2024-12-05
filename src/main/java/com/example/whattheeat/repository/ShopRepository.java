package com.example.whattheeat.repository;

import com.example.whattheeat.dto.ShopResponseDto;
import com.example.whattheeat.entity.Shop;
import com.example.whattheeat.enums.ShopState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    //상태가 오픈인 가게만 조회
    List<Shop> findByState(ShopState state);

    //랜덤 조회(전체컬럼)
    //ORDER BY RAND()는 전체 테이블 스캔이 필요해 대용량 데이터에서 매우 느림
    @Query(value = "SELECT * FROM shop WHERE state ='OPEN' ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Shop> findRandomShops();

    //랜덤 조회(선택 컬럼)
    //ORDER BY RAND()는 전체 테이블 스캔이 필요해 대용량 데이터에서 매우 느림
    @Query("SELECT new com.example.whattheeat.dto.ShopDto(s.name, s.minimumPrice, s.openTime, s.closeTime, CAST(s.state AS string)) FROM Shop s WHERE s.state = 'OPEN' ORDER BY function('RAND') LIMIT 5")
    List<ShopResponseDto> findRandomShopsSelectColum();

    // 가게 단건 조회시 메뉴도 함께 나오기
    @Query("SELECT s FROM Shop s LEFT JOIN FETCH s.menus WHERE s.id = :id")
    Optional<Shop> findByIdWithMenus(@Param("id") Integer id);

    // 폐업 상태가 아닌 가게 수 조회
    @Query("SELECT COUNT(s) FROM Shop s WHERE s.user.id = :userId AND s.state != :state")
    long countActiveShopsByUserId(@Param("userId") Long userId, @Param("state") ShopState state);


}

