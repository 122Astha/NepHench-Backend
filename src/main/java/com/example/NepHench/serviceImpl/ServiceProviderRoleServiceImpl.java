package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.SpRoleRequest;
import com.example.NepHench.model.Serviceproviderrole;
import com.example.NepHench.repository.ServiceProviderRoleRepository;
import com.example.NepHench.service.ServiceProviderRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProviderRoleServiceImpl implements ServiceProviderRoleService {

    @Autowired
    private ServiceProviderRoleRepository repo;
    @Override
    public Serviceproviderrole getById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Serviceproviderrole> findAll() {
        return repo.findAll();
    }

    @Override
    public Serviceproviderrole postServiceProviderRole(SpRoleRequest spRoleRequest) {
        Serviceproviderrole role = new Serviceproviderrole();
        role.setRoleName(spRoleRequest.getRoleName());
        role.setDescription(spRoleRequest.getDescription());
        role.setImage(spRoleRequest.getImage());
        return repo.save(role);
    }


}
