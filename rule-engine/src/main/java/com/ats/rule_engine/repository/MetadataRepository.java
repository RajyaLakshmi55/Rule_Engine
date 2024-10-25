package com.ats.rule_engine.repository;

import com.ats.rule_engine.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
}
