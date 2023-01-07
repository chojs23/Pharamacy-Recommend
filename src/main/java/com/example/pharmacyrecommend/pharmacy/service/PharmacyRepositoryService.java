package com.example.pharmacyrecommend.pharmacy.service;

import com.example.pharmacyrecommend.pharmacy.entity.Pharmacy;
import com.example.pharmacyrecommend.pharmacy.repository.PharmacyRepository;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updatePharmacyAddress(Long id, String address){
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)){
            log.error("Pharmacy is not found id: {}", id);
            return;
        }

        entity.changePharmacyAddress(address);
    }

    public void updatePharmacyAddressWithoutTransaction(Long id, String address) {
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("Pharmacy is not found id: {}", id);
            return;
        }
        entity.changePharmacyAddress(address);
    }

    @Transactional
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }
}
