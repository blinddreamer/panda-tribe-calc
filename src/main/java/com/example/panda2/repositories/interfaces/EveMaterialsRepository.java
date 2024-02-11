package com.example.panda2.repositories.interfaces;

import com.example.panda2.models.MaterialBlueprintKey;
import com.example.panda2.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EveMaterialsRepository extends JpaRepository<Material, MaterialBlueprintKey> {
    @Query(value = "SELECT * FROM evesde.\"industryActivityMaterials\" m " +
            "WHERE m.\"typeID\" = ?1 AND (m.\"activityID\" = 1 or m.\"activityID\" = 11)", nativeQuery = true)
    List<Material> findMaterialsByActivity(Integer blueprintId);
}
