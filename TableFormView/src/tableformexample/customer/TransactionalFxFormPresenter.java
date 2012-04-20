/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.customer;

import com.dooapp.fxform.FXForm;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.api.TableFormPresenter;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.api.TableFormView;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

/**
 *
 * @author Zero
 */
public abstract class TransactionalFxFormPresenter<T> implements TableFormPresenter {
    
    private TableFormView view;
    private BeanTransactionCache<T> model;
    private BooleanProperty formChanged = new SimpleBooleanProperty(false);

    public TransactionalFxFormPresenter(TableFormView view, BeanTransactionCache<T> model) {
        this.view = view;
        this.model = model;
    }

    protected BeanTransactionCache<T> getModel() {
        return model;
    }

    protected TableFormView getView() {
        return view;
    }

    @Override
    public BooleanProperty formChangedProperty() {
        return formChanged;
    }
    
    public Boolean getFormChanged() {
        return formChanged.get();
    }
    
    public void setFormChanged(Boolean changed) {
        formChanged.set(changed);
    }
    
    @Override
    public abstract void refresh();
    
    @Override
    public void changeActiveBean(Node oldForm, Object oldBean, Object newBean) {
        if (oldForm != null) {
            FXForm of = (FXForm) oldForm;
            of.dispose();
        }

        if (newBean != null) {
            T nb = (T) newBean;
            FXForm fxForm = createBeanForm(nb);

            formChanged.unbind();
            formChanged.bind(getBeanChangeBinding(nb));
            
            view.loadBeanForm(fxForm);
        }
    }

    protected abstract FXForm createBeanForm(T bean);
    
    protected abstract BooleanBinding getBeanChangeBinding(T bean);
    
    @Override
    public void addRow() {
        T bean = createNewBean();
        model.add(bean);
    }
    
    /**
     * Creates a bean with default values. This supports an "add new row" feature.
     * 
     * @return a new bean that can be inserted into the data model
     */
    protected abstract T createNewBean();

    @Override
    public void save(Object bean) {
        model.commit((T) bean);
    }
    
}
