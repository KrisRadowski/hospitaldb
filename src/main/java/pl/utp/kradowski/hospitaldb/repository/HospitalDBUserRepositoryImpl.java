package pl.utp.kradowski.hospitaldb.repository;

import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class HospitalDBUserRepositoryImpl implements HospitalDBUserRepository {

    private EntityManager em;

    public HospitalDBUserRepositoryImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public HospitalDBUser findById(long id) {
        return em.find(HospitalDBUser.class,id);
    }

    @Override
    public HospitalDBUser findByHospitalDBUsername(String userName) {
        TypedQuery<HospitalDBUser> q = em.createQuery("SELECT u from Users u WHERE u.login = :username", HospitalDBUser.class);
        q.setParameter("username",userName);
        return q.getSingleResult();
    }

    @Override
    public <S extends HospitalDBUser> S save(S s) {
        if (s.getId()==null){
            em.persist(s);
        } else {
            s = em.merge(s);
        }
        return s;
    }

    @Override
    public <S extends HospitalDBUser> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<HospitalDBUser> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<HospitalDBUser> findAll() {
        TypedQuery<HospitalDBUser> q = em.createQuery("select * from users", HospitalDBUser.class);
        return q.getResultList();
    }

    @Override
    public Iterable<HospitalDBUser> findAllById(Iterable<Long> iterable) {
        return null;
    }



    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(HospitalDBUser hospitalDBUser) {
        if (em.contains(hospitalDBUser)){
            em.remove(hospitalDBUser);
        } else {
            em.merge(hospitalDBUser);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends HospitalDBUser> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
