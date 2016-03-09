package zdislava.model.dto.security.rights;

import java.io.Serializable;

/**
 * Описывает права на доступ и изменение
 *
 * @author Zdislava
 *
 */
public class VisibleEditable implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Поля">
    private boolean visible = false;
    private boolean enabled = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Проверяет секции на просмотр.
     *
     * @return true-если можно промсмотреть, false - иначе
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Назначает доступность на просмотр.
     *
     * @param visible доступность на просмотр
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * Проверяет доступность на редактирование.
     *
     * @return true-если можно редактировать, false - иначе
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Назначает доступность на редактирование.
     *
     * @param enabled доступность на редактирование
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    //</editor-fold>
}

