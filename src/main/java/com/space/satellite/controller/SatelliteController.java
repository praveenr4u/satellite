package com.space.satellite.controller;

import com.space.satellite.model.Record;
import com.space.satellite.model.SatelliteRequest;
import com.space.satellite.model.SatelliteResponse;
import com.space.satellite.respository.RecordRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller

@RequiredArgsConstructor
public class SatelliteController {


    private String name;

    private final RecordRepository recordRepository;

    @GetMapping
    public ResponseEntity<SatelliteResponse> getSatelliteDataByIds(@RequestParam("ids") List<Long> ids) {
        SatelliteResponse SatelliteResponse = new SatelliteResponse();

        Iterable<Record> records = recordRepository.findAllById(ids);
        SatelliteResponse.setPayload(records);

        return new ResponseEntity(SatelliteResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<SatelliteResponse> deleteSatelliteDataById(@RequestParam("id") Long id) {
        Optional<Record> recordOptional = recordRepository.findById(id);

        if(recordOptional.isPresent()) {
            recordRepository.delete(recordOptional.get());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<SatelliteResponse> updateData(@RequestParam("id") Long id, @RequestBody SatelliteRequest SatelliteRequest) {
        Optional<Record> recordOptional = recordRepository.findById(id);

        if(recordOptional.isPresent()) {
            SatelliteResponse SatelliteResponse = new SatelliteResponse();

            Record record = recordOptional.get();
            record.setData(String.valueOf(SatelliteRequest.getPayload()));

            SatelliteResponse.setPayload(recordRepository.save(record));
            return new ResponseEntity(SatelliteResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<SatelliteResponse> saveNewData(@RequestBody SatelliteRequest SatelliteRequest) {
        SatelliteResponse SatelliteResponse = new SatelliteResponse();

        Record record = new Record();
        record.setData(String.valueOf(SatelliteRequest.getPayload()));
        SatelliteResponse.setPayload(recordRepository.save(record));

        return new ResponseEntity(SatelliteResponse, HttpStatus.OK);
    }
}
