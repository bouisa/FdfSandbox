/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.mdpconfig.data;

import gov.nasa.gsfc.fdf.datalib.api.BeanDao;
import gov.nasa.gsfc.fdf.datalib.api.exceptions.NonexistentEntityException;
import gov.nasa.gsfc.fdf.datalib.api.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author abouis
 */
public class MdpConfigJpaController implements Serializable, BeanDao<MdpConfig> {

    public MdpConfigJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MdpConfig mdpConfig) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mdpConfig);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMdpConfig(mdpConfig.getConfigName()) != null) {
                throw new PreexistingEntityException("MdpConfig " + mdpConfig + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MdpConfig mdpConfig) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mdpConfig = em.merge(mdpConfig);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = mdpConfig.getConfigName();
                if (findMdpConfig(id) == null) {
                    throw new NonexistentEntityException("The mdpConfig with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MdpConfig mdpConfig;
            try {
                mdpConfig = em.getReference(MdpConfig.class, id);
                mdpConfig.getConfigName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mdpConfig with id " + id + " no longer exists.", enfe);
            }
            em.remove(mdpConfig);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MdpConfig> findMdpConfigEntities() {
        return findMdpConfigEntities(true, -1, -1);
    }

    public List<MdpConfig> findMdpConfigEntities(int maxResults, int firstResult) {
        return findMdpConfigEntities(false, maxResults, firstResult);
    }

    private List<MdpConfig> findMdpConfigEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MdpConfig.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MdpConfig findMdpConfig(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MdpConfig.class, id);
        } finally {
            em.close();
        }
    }

    public int getMdpConfigCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MdpConfig> rt = cq.from(MdpConfig.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public List<MdpConfig> retrieve() {
        List<MdpConfig> resultList = findMdpConfigEntities();
        return resultList;
    }

    @Override
    public void add(MdpConfig bean) throws Exception {
        create(bean);
    }

    @Override
    public void remove(MdpConfig bean) throws Exception {
        destroy(bean.getConfigName());
    }

    @Override
    public void save(MdpConfig bean) throws Exception {
        edit(bean);
    }
    
}
