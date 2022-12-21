package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.ClickApps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickAppsRepository extends JpaRepository<ClickApps, Integer> {
}