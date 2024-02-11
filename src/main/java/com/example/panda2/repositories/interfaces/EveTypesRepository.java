package com.example.panda2.repositories.interfaces;

import com.example.panda2.models.EveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EveTypesRepository extends JpaRepository<EveType, Integer> {
    Optional<EveType> findEveTypeByTypeId(int typeId);
    Optional<EveType> findEveTypeByTypeName(String typeName);
}
