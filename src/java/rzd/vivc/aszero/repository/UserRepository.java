package rzd.vivc.aszero.repository;

import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rzd.vivc.aszero.dto.form.Form;
import zdislava.model.dto.security.users.ExistingUserExeption;
import zdislava.model.dto.security.users.User;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 * Нужен для сохранения - извлечения из БД объектов, связанных с пользователями
 *
 *
 * @author VVolgina
 *
 */
public class UserRepository extends BaseRepository {

    /**
     * Добавление нового пользователя
     *
     * @param elem пользователь
     * @throws ExistingUserExeption если пользователь с таким логином уже
     * существует
     * @throws IllegalArgumentException если у пользователя не указан логин или
     * пароль
     */
    public void addNewUser(User elem) throws ExistingUserExeption,
            IllegalArgumentException {
        // проверка, что у добавляемого пользователя введены логин и пароль
        if (elem.getLogin().isEmpty() || elem.getLogin() == null
                || elem.getPassword().isEmpty() || elem.getPassword() == null) {
            throw new IllegalArgumentException(
                    "Необходимо указать логин и пароль");
        }
        // пробуем извлечь из БД пользователя с данным логином
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        User bd = null;
        try {
            t = sess.beginTransaction();


            bd = (User) sess.bySimpleNaturalId(User.class).load(elem.getLogin());
            // Если не извлекся - создаем нового пользователя
            // если такой пользователь уже существует, выдаем ошибку
            if (bd == null) {
                sess.persist(elem);
            }
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
        if (bd != null) {

            throw new ExistingUserExeption(
                    "Пользователь с данным именем уже существует");
        }


    }

    /**
     * Изменение информации о пользователе
     *
     * @param elem пользователь
     * @throws ExistingUserExeption отсутствует пользователь с указаным логином
     * @throws IllegalArgumentException если у пользователя не указан логин или
     * пароль
     */
    public void changeExistingUser(User elem) throws ExistingUserExeption,
            IllegalArgumentException {
        // проверка, что у изменяемого пользователя введены логин и пароль
        if (elem.getLogin().isEmpty() || elem.getLogin() == null
                || elem.getPassword().isEmpty() || elem.getPassword() == null) {
            throw new IllegalArgumentException(
                    "Необходимо указать логин и пароль");
        }
        // пробуем извлечь из БД пользователя с данным логином
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            // User bd = (User)sess.bySimpleNaturalId(User.class).load(elem.getLogin());
            // Если не извлекся - выдаем ошибку
            // если такой пользователь уже существует,обновляем информацию о нем
            sess.saveOrUpdate(elem);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

    }

    /**
     * Изменение информации о пользователе
     *
     * @param elem пользователь
     * @throws ExistingUserExeption уже есть пользователь с новым логином
     * @throws IllegalArgumentException если у пользователя не указан логин или
     * пароль
     */
    public void changeExistingUserWhithLoginCheck(User elem) throws ExistingUserExeption,
            IllegalArgumentException {
        // проверка, что у изменяемого пользователя введены логин и пароль
        if (elem.getLogin().isEmpty() || elem.getLogin() == null
                || elem.getPassword().isEmpty() || elem.getPassword() == null) {
            throw new IllegalArgumentException(
                    "Необходимо указать логин и пароль");
        }
        // пробуем извлечь из БД пользователя с данным логином

        User bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = (User) sess.bySimpleNaturalId(User.class).load(elem.getLogin());
            if (bd == null) {
                sess.saveOrUpdate(elem);
            }
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        if (bd != null) {

            throw new ExistingUserExeption(
                    "Пользователь с данным именем уже существует");
        }
        // User bd = (User)sess.bySimpleNaturalId(User.class).load(elem.getLogin());
        // Если не извлекся - выдаем ошибку
        // если такой пользователь уже существует,обновляем информацию о нем


    }

    /*
     public void changeExistingUser(User elem) throws ExistingUserExeption,
     IllegalArgumentException {
     // проверка, что у изменяемого пользователя введены логин и пароль
     if (elem.getLogin().isEmpty() || elem.getLogin() == null
     || elem.getPassword().isEmpty() || elem.getPassword() == null) {
     throw new IllegalArgumentException(
     "Необходимо указать логин и пароль");
     }
     // пробуем извлечь из БД пользователя с данным логином
     Session sess = SessionFactorySingleton.getSessionFactoryInstance()
     .openSession();
     Transaction t = sess.beginTransaction();
     User bd = (User)sess.bySimpleNaturalId(User.class).load(elem.getLogin());
     // Если не извлекся - выдаем ошибку
     // если такой пользователь уже существует,обновляем информацию о нем
     if (bd == null) {
     throw new ExistingUserExeption(
     "Пользователь с данным именем не существует");
     } else {
     sess.saveOrUpdate(bd);
     }
     t.commit();
     sess.close();
     }

     /*
     /**
     * Извлекает из БД информацию о пользователи, при
     * условии, что логин и пароль введены верно
     *
     * @param login логин
     * @param password пароль
     * @return пользователь
     * @throws ExistingUserExeption если не совпадает логин или пароль
     */
    public User getUserWithoutRights(String login, String password)
            throws ExistingUserExeption {

        // пытаемся извлечь из БД пользователя с данным логином
        User bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = (User) sess.bySimpleNaturalId(User.class).load(login);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        // если такого нет - выдаем ошибку
        if (bd == null) {
            throw new ExistingUserExeption(
                    "Пользователь с данным логином не существует");
        }

        if (!bd.getPassword().equals(password)) {
            throw new ExistingUserExeption(
                    "Введен неверный пароль");
        }

        if (bd.isDeleted()) {
            throw new ExistingUserExeption(
                    "Пользователь заблокирован");
        }

        if (bd.getDepartment().isDeleted()) {
            throw new ExistingUserExeption(
                    "Подразделение, в котором числится пользователь, удалено");
        }

        return bd;
    }

    /**
     * Извлекает из БД информацию о пользователи
     *
     * @param login логин
     * @return пользователь
     */
    public User getUserForEdit(String login) {

        // пытаемся извлечь из БД пользователя с данным логином
        User bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();
            bd = (User) sess.bySimpleNaturalId(User.class).load(login);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        // если такого нет - выдаем ошибку
        if (bd == null) {
            bd = new User();
        }

        return bd;
    }

    /**
     * Извлекает из БД весь список неудаленных пользователей, отсортированный по
     * фамилии
     *
     * @return список пользователей
     */
    public List<User> getAllUsers() {
        List<User> dbList = null;
        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();
            // TODO подумать о критериях
            dbList = (List<User>) sess.createQuery(
                    "from User order by login").list();
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return dbList;
    }

    public List<User> getAllUsersByDep(long id) {
        List<User> dbList = null;
        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();
            // TODO подумать о критериях
            dbList = (List<User>) sess.createQuery(
                    "from User as u where u.department.id=:dep").setLong("dep", id).list();
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
        return dbList;
    }

    public List<User> getAllUsersByDepWithRights(long id) {
        List<User> dbList = null;
        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();
            // TODO подумать о критериях
            dbList = (List<User>) sess.createQuery(
                    "select distinct u from User as u left join fetch u.forms where u.department.id=:dep").setLong("dep", id).list();
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
        return dbList;
    }

    public User getUserWithForms(long id) {

        // пытаемся извлечь из БД пользователя с данным логином
        User bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();
            bd = (User) sess.get(User.class, id);
            if (bd != null) {
                List<Form> forms = bd.getForms();
                for (Form form : forms) {
                    int u = 9;
                }
            }
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(UserRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
        // если такого нет - выдаем ошибку
        if (bd == null) {
            bd = new User();
        }
        return bd;
    }

    public static void main(String[] args) {
        UserRepository rep = new UserRepository();
        try {
            User userWithoutRights = rep.getUserWithoutRights("fegv3w4t243", "43");
            System.out.println(userWithoutRights);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
