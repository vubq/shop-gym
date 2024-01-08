package com.web.shopgym.services.impl;

import com.web.shopgym.entities.*;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.ProductDetailRepository;
import com.web.shopgym.services.ProductDetailService;
import com.web.shopgym.utils.BaseSpecification;
import com.web.shopgym.utils.SearchCriteria;
import com.web.shopgym.utils.SearchOperation;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public Page<ProductDetail> getAllBySearchCriteria(DataTableRequest dataTableRequest) {
        PageRequest pageable = dataTableRequest.toPageable();
        BaseSpecification<ProductDetail> specCodeContains = new BaseSpecification<>(
                SearchCriteria.builder().keys(new String[]{ProductDetail.Fields.code}).operation(SearchOperation.CONTAINS)
                        .value(dataTableRequest.getFilter().trim().toUpperCase()).build());

        return this.productDetailRepository.findAll(Specification.where(specCodeContains), pageable);
    }

    @Override
    public Page<ProductDetail> getListOfProductDetailsByCriteriaWebShop(
            DataTableRequest dataTableRequest,
            List<String> categories,
            List<String> sizes,
            List<String> colors,
            List<String> materials,
            List<Double> priceApprox) {
        PageRequest pageable = dataTableRequest.toPageable();
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);
                query.groupBy(root.get("product"));
                List<Predicate> predicates = new ArrayList<>();
                Join<ProductDetail, Product> productDetailProductJoin = root.join("product");
                Join<ProductDetail, Size> productDetailSizeJoin = root.join("size");
                Join<ProductDetail, Color> productDetailColorJoin = root.join("color");
                Join<ProductDetail, Material> productDetailMaterialJoin = root.join("material");
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(productDetailProductJoin.get("name")), "%" + dataTableRequest.getFilter() + "%"));
                if (categories.size() > 0) {
                    predicates.add(criteriaBuilder.and(productDetailProductJoin.get("category").get("id").in(categories)));
                }
                if (sizes.size() > 0) {
                    predicates.add(criteriaBuilder.and(productDetailSizeJoin.get("id").in(sizes)));
                }
                if (colors.size() > 0) {
                    predicates.add(criteriaBuilder.and(productDetailColorJoin.get("id").in(colors)));
                }
                if (materials.size() > 0) {
                    predicates.add(criteriaBuilder.and(productDetailMaterialJoin.get("id").in(materials)));
                }
                if(priceApprox.size() == 2) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(productDetailProductJoin.get("price"), priceApprox.get(0))));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(productDetailProductJoin.get("price"), priceApprox.get(1))));
                }
                query.where(predicates.toArray(new Predicate[]{}));
                return null;
            }
        };
        return this.productDetailRepository.findAll(specification, pageable);
    }

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return this.productDetailRepository.save(productDetail);
    }

    @Override
    public List<ProductDetail> getAllByProductId(String productId) {
        return this.productDetailRepository.findAllByProductId(productId);
    }

    @Override
    public List<String> getAllSizeIdByProductId(String productId) {
        return this.productDetailRepository.findAllSizeIdByProductId(productId);
    }

    @Override
    public List<String> getAllColorIdByProductId(String productId) {
        return this.productDetailRepository.findAllColorIdByProductId(productId);
    }

    @Override
    public List<String> getAllMaterialIdByProductId(String productId) {
        return this.productDetailRepository.findAllMaterilIdByProductId(productId);
    }

    @Override
    public List<ProductDetail> getAllProductDetailInOfStock() {
        return this.productDetailRepository.getAllProductDetailInOfStock();
    }

    @Override
    public Optional<ProductDetail> findById(String id) {
        return this.productDetailRepository.findById(id);
    }

    @Override
    public List<ProductDetail> saveAll(List<ProductDetail> productDetails) {
        return this.productDetailRepository.saveAll(productDetails);
    }
}
