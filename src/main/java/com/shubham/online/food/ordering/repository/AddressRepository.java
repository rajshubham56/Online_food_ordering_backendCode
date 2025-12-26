package com.shubham.online.food.ordering.repository;

import com.shubham.online.food.ordering.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
