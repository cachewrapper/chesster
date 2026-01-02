package org.cachewrapper.query.repository;

import org.cachewrapper.query.domain.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ViewRepository<T extends View, K> extends JpaRepository<T, K> {}