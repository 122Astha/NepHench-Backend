package com.example.NepHench.repository;

import com.example.NepHench.model.Serviceproviderrole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderRoleRepository extends JpaRepository<Serviceproviderrole, Integer> {
    Serviceproviderrole findByRoleName(String roleName);
}