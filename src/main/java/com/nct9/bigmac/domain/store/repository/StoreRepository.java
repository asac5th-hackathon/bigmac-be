
package com.nct9.bigmac.domain.store.repository;

import com.nct9.bigmac.domain.store.dto.StoreListResponseInterface;
import com.nct9.bigmac.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT\n" +
            "    S.id AS id,\n" +
            "    S.name AS name,\n" +
            "    S.latitude AS latitude,\n" +
            "    S.longitude AS longitude,\n" +
            "    CASE\n" +
            "        WHEN :categoryId IS NOT NULL AND EXISTS (\n" +
            "            SELECT 1\n" +
            "            FROM product AS P\n" +
            "            WHERE P.store_id = S.id AND P.category_id = :categoryId\n" +
            "        )\n" +
            "        THEN (\n" +
            "            SELECT MIN(P.price)\n" +
            "            FROM product AS P\n" +
            "            WHERE P.store_id = S.id AND P.category_id = :categoryId\n" +
            "        )\n" +
            "        ELSE NULL\n" +
            "    END AS price\n" +
            "FROM\n" +
            "    store AS S\n" +
            "WHERE\n" +
            "    ST_Distance_Sphere(Point(:longitude, :latitude), Point(S.longitude, S.latitude)) <= :distance",
            nativeQuery = true)
    public List<StoreListResponseInterface> getStoreListByDistance(@Param("latitude") double latitude,
                                                                   @Param("longitude") double longitude,
                                                                   @Param("distance") Integer distance,
                                                                   @Param("categoryId") Long categoryId);
    Optional<Store> findByName(String name);
}

