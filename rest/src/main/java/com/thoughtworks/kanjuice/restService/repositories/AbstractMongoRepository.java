package com.thoughtworks.kanjuice.restService.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractMongoRepository<T, ID extends Serializable, R extends MongoRepository<T, ID>> {

    protected R repository;

    public AbstractMongoRepository(R repository) {
        this.repository = repository;
    }

    public <S extends T> List<S> save(Iterable<S> entites) {
        return repository.save(entites);
    }

    public <S extends T> S save(S entity) {
        return repository.save(entity);
    }

    public T findOne(ID id) {
        return repository.findOne(id);
    }

    public boolean exists(ID id) {
        return repository.exists(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Iterable<T> findAll(Iterable<ID> ids) {
        return repository.findAll(ids);
    }

    public long count() {
        return repository.count();
    }

    public void delete(ID id) {
        repository.delete(id);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }

    public void delete(Iterable<? extends T> entities) {
        repository.delete(entities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public <S extends T> List<S> insert(Iterable<S> entities) {
        return repository.insert(entities);
    }

    public <S extends T> S insert(S entity) {
        return repository.insert(entity);
    }
}