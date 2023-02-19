package com.raf.RafCloudBack.repositories;

import com.raf.RafCloudBack.models.Machine;
import com.raf.RafCloudBack.models.MachineStatus;
import com.raf.RafCloudBack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    @Query("SELECT DISTINCT m FROM Machine m LEFT JOIN m.runningPeriods p WHERE ((:dateStarted IS NULL OR p.dateStarted <= :dateStarted) AND " +
            "(:dateStopped IS NULL OR p.dateStopped >= :dateStopped)) AND (m.user = :user) AND ((:createdAtLowerBound IS NULL OR m.createdAt >= :createdAtLowerBound) " +
            "AND (:createdAtUpperBound IS NULL OR m.createdAt <= :createdAtUpperBound)) AND" +
            "(m.status = :status OR :status IS NULL) AND (m.name LIKE CONCAT(:name, '%') OR :name IS NULL)")
    List<Machine> findByFilters(
            @Param("user") User user,
            @Param("status") MachineStatus status,
            @Param("name") String name,
            @Param("dateStarted") Timestamp dateStarted,
            @Param("dateStopped") Timestamp dateStopped,
            @Param("createdAtLowerBound") Timestamp createdAtLowerBound,
            @Param("createdAtUpperBound") Timestamp createdAtUpperBound
    );

    @Modifying
    @Query("UPDATE Machine m set m.status = :status WHERE m.id = :id")
    @Transactional
    void updateStatus(@Param("id") Long id, @Param("status") MachineStatus status);

    @Modifying
    @Query("UPDATE Machine m set m.active = false WHERE m.id = :id")
    @Transactional
    void deleteMachine(@Param("id") Long id);
}
