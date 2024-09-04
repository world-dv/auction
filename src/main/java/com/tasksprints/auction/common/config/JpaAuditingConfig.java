package com.tasksprints.auction.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//@EnableJpaAuditing 제거 -> 프로젝트 선언부에 이미 선언되어 있음
public class JpaAuditingConfig {
}
