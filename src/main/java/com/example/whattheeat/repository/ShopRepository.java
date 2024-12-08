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
public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByName(String name);

    //상태가 오픈인 가게만 조회
    List<Shop> findByState(ShopState state);

    //랜덤 조회(전체컬럼)
    //ORDER BY RAND()는 전체 테이블 스캔이 필요해 대용량 데이터에서 매우 느림
//    @Query(value = "SELECT * FROM Shop s WHERE s.state ='OPEN' ORDER BY RAND() LIMIT 5", nativeQuery = true)
//    List<Shop> findRandomShops();

    //랜덤 조회(선택 컬럼)
    //ORDER BY RAND()는 전체 테이블 스캔이 필요해 대용량 데이터에서 매우 느림
//    @Query("SELECT * FROM Shop s WHERE s.state = 'OPEN' ORDER BY function('RAND')")
//    List<Shop> findRandomShopsWithSelectedColumns();
    @Query(value = "SELECT * FROM shop s WHERE s.state = 'OPEN' ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Shop> findRandomShopsWithSelectedColumns();


    // 가게 단건 조회시 메뉴도 함께 나오기
    @Query("SELECT s FROM Shop s LEFT JOIN FETCH s.menus WHERE s.id = :id")
    Optional<Shop> findByIdWithMenus(@Param("id") Long id);

    // 폐업 상태가 아닌 가게 수 조회
    @Query("SELECT COUNT(s) FROM Shop s WHERE s.owner.id = :ownerId AND s.state != 'CLOSED'")
    long countActiveShopsByUserId(@Param("ownerId") Long ownerId);
}