package com.web.shopgym.controllers;

import com.web.shopgym.dtos.FilterProductAttributeDTO;
import com.web.shopgym.entities.ProductDetail;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.searchForm.ProductDetailSearchForm;
import com.web.shopgym.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/product-detail")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping
    public DataTableResponse getAllBySearchCriteria(DataTableRequest dataTableRequest) {
        Page<ProductDetail> result = null;
        result = this.productDetailService.getAllBySearchCriteria(dataTableRequest);

        return DataTableResponse.build().ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }

    @GetMapping("/get-all-product-detail-in-of-stock")
    public Response getAllProductDetailInOfStock() {
        return Response.build().ok().data(this.productDetailService.getAllProductDetailInOfStock());
    }

    @PostMapping("/filter-product-attribute")
    public Response filterProductAttributes(@RequestBody FilterProductAttributeDTO filter) {
        List<ProductDetail> productDetails = this.productDetailService.filterProductAttributes(filter);
        List<String> listOfAvailableSizes = new ArrayList<>();
        List<String> listOfAvailableColors = new ArrayList<>();
        List<String> listOfAvailableMaterials = new ArrayList<>();
        Integer totalProductsAvailable = 0;
        for (ProductDetail productDetail : productDetails) {
            totalProductsAvailable += productDetail.getQuantity();
            if (!listOfAvailableSizes.contains(productDetail.getSize().getId())) {
                listOfAvailableSizes.add(productDetail.getSize().getId());
            }
            if (!listOfAvailableColors.contains(productDetail.getColor().getId())) {
                listOfAvailableColors.add(productDetail.getColor().getId());
            }
            if (!listOfAvailableMaterials.contains(productDetail.getMaterial().getId())) {
                listOfAvailableMaterials.add(productDetail.getMaterial().getId());
            }
        }
        Map<String, Object> productFilterList = new HashMap<>();
        productFilterList.put("listOfAvailableSizes", listOfAvailableSizes);
        productFilterList.put("listOfAvailableColors", listOfAvailableColors);
        productFilterList.put("listOfAvailableMaterials", listOfAvailableMaterials);
        productFilterList.put("totalProductsAvailable", totalProductsAvailable);
        return Response.build().ok().data(productFilterList);
    }

    @GetMapping("/get-product-detail-by-attributes")
    public Response getProductDetailByAttributes(
            @RequestParam(value = "productId") String productId,
            @RequestParam(value = "sizeId") String sizeId,
            @RequestParam(value = "colorId") String colorId,
            @RequestParam(value = "materialId") String materialId) {
        return Response.build().ok().data(this.productDetailService.getProductDetailByAttributes(
                productId,
                sizeId,
                colorId,
                materialId
        ));
    }

    @PostMapping("/get-all-product-detail-by-list-id")
    public Response getAllProductDetailByListId(@RequestBody ProductDetailSearchForm searchForm) {
        List<ProductDetail> productDetails = new ArrayList<>();
        searchForm.getListProductDetailId().forEach(productDetailId -> {
            productDetails.add(this.productDetailService.findById(productDetailId).get());
        });
        return Response.build().ok().data(productDetails);
    }
}
