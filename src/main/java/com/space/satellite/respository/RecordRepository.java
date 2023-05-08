package com.space.satellite.respository;

import com.space.satellite.model.Record;
import lombok.Getter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RecordRepository extends CrudRepository<Record, Long> {


}
