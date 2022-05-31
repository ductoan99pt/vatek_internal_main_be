package com.vatek.project.respository;

import com.vatek.project.entity.MasterDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MasterDataRepository extends JpaRepository<MasterDataEntity,Long>, JpaSpecificationExecutor<MasterDataEntity> {

}
