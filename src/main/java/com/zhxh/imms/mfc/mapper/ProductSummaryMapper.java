package com.zhxh.imms.mfc.mapper;

import com.zhxh.data.mapper.CrudMapper;
import com.zhxh.imms.mfc.domain.ProductSummary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface ProductSummaryMapper extends CrudMapper<ProductSummary> {
}
