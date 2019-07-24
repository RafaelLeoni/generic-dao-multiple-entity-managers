package br.com.azulseguros.apisinistro2.aberturaaviso.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDao {

    @PersistenceContext(unitName = "ApiSinistro2CamaroSinistro")
    protected EntityManager emSinistro;
    
    @PersistenceContext(unitName = "ApiSinistro2CamaroChaves")
    protected EntityManager emChaves;

    private Map<String, EntityManager> factory;

    public static final String ENTITY_MANAGER_SINISTRO = "emSinistro";
    public static final String ENTITY_MANAGER_CHAVES = "emChaves";
    
    @PostConstruct
    public void init() {
    	factory = new HashMap<>();
    	factory.put(ENTITY_MANAGER_SINISTRO, emSinistro);
    	factory.put(ENTITY_MANAGER_CHAVES, emChaves);
    }

    public <T> T save(final T t, String entityManager) {
    	factory.get(entityManager).persist(t);
        return t;
    }

    public <T> void delete(final Object id, String entityManager, Class<T> type) {
    	factory.get(entityManager).remove(factory.get(entityManager).getReference(type, id));
    }

    public <T> T find(final Object id, String entityManager, Class<T> type) {
        return (T) factory.get(entityManager).find(type, id);
    }

    public <T> T update(final T t, String entityManager) {
        return factory.get(entityManager).merge(t);    
    }
}