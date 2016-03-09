package zdislava.model.dto.security.users;

/**
 * Ошибка выдается повторного создания в БД пользователя с уже существующим
 * логином, неправильно введенном пароле и.т.д
 * 
 * @author Zdislava
 * 
 */
public class ExistingUserExeption extends Exception {

	private static final long serialVersionUID = -8925745703549397857L;

	/**
	 * Создает экземпляр класса
	 * 
	 * @param arg0
	 *            - информация об ошибке
	 */
	public ExistingUserExeption(String arg0) {
		super(arg0);
	}

}
