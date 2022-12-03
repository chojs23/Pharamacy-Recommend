package com.example.pharmacyrecommend.pharmacy.repository;


import com.example.pharmacyrecommend.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {

}
