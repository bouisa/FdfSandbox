/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.mdpconfig;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.view.FXFormSkinFactory;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.api.TableFormPresenter;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.api.TableFormView;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javafxdata.datasources.protocol.ObjectDataSource;
import tableformexample.mdpconfig.data.MdpConfig;
import tableformexample.mdpconfig.data.MdpConfigFxBean;

/**
 *
 * @author Zero
 */
public class MdpConfigTableFormPresenter implements TableFormPresenter {
    
    private TableFormView view;
    private BeanTransactionCache<MdpConfigFxBean> model;
    private BooleanProperty formChanged = new SimpleBooleanProperty(false);

    /**
     * Constructor
     * 
     * @param view 
     */
    public MdpConfigTableFormPresenter(TableFormView view, BeanTransactionCache<MdpConfigFxBean> model) {
        this.view = view;
        this.model = model;

//        ConfigEditorController.class.getResource("/fxdataexamples/ConfigEditorController.css").toExternalForm());
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
    public void refresh() {
        model.refreshCache("configName");
        ObjectDataSource<MdpConfigFxBean> dataSource = model.getCachedData();
        
        dataSource.getNamedColumn("configName").setText("Config Name");
        dataSource.getNamedColumn("name").setCellValueFactory(new PropertyValueFactory("name"));

        view.loadData(dataSource);
    }
    
    @Override
    public void changeActiveBean(Node oldForm, Object oldBean, Object newBean) {
        if(oldForm != null) {
            FXForm of = (FXForm) oldForm;
            of.dispose();
        }
        
        if (newBean != null) {
            MdpConfigFxBean nb = (MdpConfigFxBean) newBean;
            FXForm fxForm = new FXForm();
            
            fxForm.setSkin(FXFormSkinFactory.INLINE_FACTORY.createSkin(fxForm));
            fxForm.setSource(nb);
            fxForm.setTitle(" Edit Config: " + nb.getConfigName());
            
            formChanged.unbind();
            formChanged.bind(getBeanChangeBinding(nb));
            
            view.loadBeanForm(fxForm);
        }
    }
    
    private BooleanBinding getBeanChangeBinding(MdpConfigFxBean bean) {
        BooleanBinding changed = 
                bean.configNameProperty().isNotEqualTo(bean.getConfigName())
                .or(bean.dataTypeProperty().isNotEqualTo(bean.getDataType()))
                .or(bean.heartbeatIntervalProperty().isNotEqualTo(bean.getHeartbeatInterval()));
        
        return changed;
    }

    @Override
    public void addRow() {
        MdpConfig c = new MdpConfig("Ruby Weapon");
        c.setDataType("Fire");
        c.setHeartbeatInterval(30);
        model.add(new MdpConfigFxBean(c));
    }
    
    @Override
    public void save(Object bean) {
        model.commit((MdpConfigFxBean) bean);
    }
    
}
