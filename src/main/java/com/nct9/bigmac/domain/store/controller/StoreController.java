package com.nct9.bigmac.domain.store.controller;

import com.nct9.bigmac.domain.store.dto.StoreInfoDto;
import com.nct9.bigmac.domain.store.dto.StoreListRequestDto;
import com.nct9.bigmac.domain.store.dto.StoreListResponseDto;
import com.nct9.bigmac.domain.store.dto.StoreProductsDto;
import com.nct9.bigmac.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/{id}")
    public StoreInfoDto getStoreInfo(@PathVariable Long id, @RequestParam(required = false) Long categoryId) {

        return storeService.getStoreInfo(id, categoryId);
    }

    @GetMapping("/{id}/details")
    public StoreProductsDto getStoreProducts(@PathVariable Long id) {
        return storeService.getStoreProducts(id);
    }

    @GetMapping("")
    public List<StoreListResponseDto> getStoreListByFilter(@ModelAttribute StoreListRequestDto storeListRequestDto) {
        List<StoreListResponseDto> storeList = storeService.getStoreListByFilter(storeListRequestDto);
        return storeList;
    }
}
