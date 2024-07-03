package com.nct9.bigmac.domain.store.repository;

import com.nct9.bigmac.domain.store.dto.StoreListResponseInterface;
import com.nct9.bigmac.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT\n" +
            "    S.id AS id,\n" +
            "    S.name AS name,\n" +
            "    S.latitude AS latitude,\n" +
            "    S.longitude AS longitude,\n" +
            "    MIN(P.price) AS price,\n" +
            "    ST_Distance_Sphere(Point(:longitude, :latitude), Point(S.longitude, S.latitude)) AS distance\n" +
            "FROM\n" +
            "    store AS S\n" +
            "LEFT JOIN product AS P ON S.id = P.store_id\n" +
            "WHERE\n" +
            "    (:categoryId IS NULL OR S.id IN (SELECT store_id FROM product WHERE category_id = :categoryId))\n" +
            "    AND ST_Distance_Sphere(Point(:longitude, :latitude), Point(S.longitude, S.latitude)) <= :distance\n" +
            "GROUP BY\n" +
            "    S.id, S.name, S.latitude, S.longitude\n" +
            "ORDER BY\n" +
            "    distance",
            nativeQuery = true)
    public List<StoreListResponseInterface> getStoreListByDistance(@Param("latitude") double latitude,
                                                                   @Param("longitude") double longitude,
                                                                   @Param("distance") Integer distance,
                                                                   @Param("categoryId") Long categoryId);
}

