package com.zixun.purchase.domain.mappers;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午11:55
 * @version: V1.0
 * @modified by:
 */
public interface BasicObjectMapper<SOURCE, TARGET> {
    @Mappings({})
    @InheritConfiguration
    TARGET to(SOURCE var1);

    @InheritConfiguration
    List<TARGET> to(List<SOURCE> var1);

    @InheritInverseConfiguration
    SOURCE from(TARGET var1);

    @InheritInverseConfiguration
    List<SOURCE> from(List<TARGET> var1);
}
