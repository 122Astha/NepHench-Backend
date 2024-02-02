package com.example.NepHench.service;

import com.example.NepHench.beans.SpRoleRequest;
import com.example.NepHench.model.Serviceproviderrole;

import java.util.List;

public interface ServiceProviderRoleService {

    Serviceproviderrole getById(Integer id);

    List<Serviceproviderrole> findAll();

    Serviceproviderrole postServiceProviderRole(SpRoleRequest spRoleRequest);
}
