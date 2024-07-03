package com.nct9.bigmac.domain.store.repository;


import com.nct9.bigmac.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
