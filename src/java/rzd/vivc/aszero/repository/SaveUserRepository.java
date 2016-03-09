/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import zdislava.common.dto.configuration.SessionFactorySingleton;
import zdislava.model.dto.security.users.User;
import zdislava.model.dto.security.users.UserType;

/**
 * Объекты из БД для SaveUserPageBean1
 *
 * @author VVolgina
 */
public class SaveUserRepository {

    private User user;
    private List<UserType> userTypes;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserType> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(List<UserType> userTypes) {
        this.userTypes = userTypes;
    }

    /**
     * Загрузить список доступных типов пользователей и создать пользователя для
     * редактирования
     *
     * @param id id, если >0 из БД будет загружен соответствующий пользователь.
     * @param dep если id <0 - будет создан новый пользователь и для него
     * загружена информация об отделе
     */
    public void loadAll(long id, long dep) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

        userTypes = (new UserTypeRepository()).getAllNonDeleted(UserType.class, sess);
        loadUser(id, dep, sess);
        t.commit();
        } catch (Exception e) {
            Logger.getLogger(SaveUserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(SaveUserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(SaveUserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


    }
    
     /**
     * создать пользователя для
     * редактирования
     *
     * @param id id, если >0 из БД будет загружен соответствующий пользователь.
     * @param dep если id <0 - будет создан новый пользователь и для него
     * загружена информация об отделе
     */
    public void loadUser(long id, long dep) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

        loadUser(id, dep, sess);
        t.commit();
        } catch (Exception e) {
            Logger.getLogger(SaveUserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(SaveUserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(SaveUserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


    }

     /**
     * создать пользователя для
     * редактирования
     *
     * @param id id, если >0 из БД будет загружен соответствующий пользователь.
     * @param dep если id <0 - будет создан новый пользователь и для него
     * загружена информация об отделе
     */
    private void loadUser(long id, long dep, Session sess) {
         if (id != 0) {
             //загружаем пользователя
            user = new User(id);
            user = (new UserRepository()).get(user, sess);
            user.getUserType().getId();
        } else {
             //создаем нового и загружаем ему отдел
            user = new User();
            user.setDepartment((new DepartmentRepository()).get(dep, sess));
        }
    }
}
