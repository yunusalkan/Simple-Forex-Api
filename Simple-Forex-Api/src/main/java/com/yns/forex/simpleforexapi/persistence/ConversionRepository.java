package com.yns.forex.simpleforexapi.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface ConversionRepository extends PagingAndSortingRepository<ConversionEntity, Long> {

    ConversionEntity findByTransactionId(String transactionId);

    List<ConversionEntity> findAllByTransactionDate(LocalDate transactionDate, Pageable pageable);
}
