/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.mdpconfig;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.view.FXFormSkinFactory;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.api.TableFormView;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javafxdata.datasources.protocol.ObjectDataSource;
import tableformexample.customer.TransactionalFxFormPresenter;
import tableformexample.mdpconfig.data.MdpConfig;
import tableformexample.mdpconfig.data.MdpConfigFxBean;

/**
 *
 * @author Zero
 */
public class MdpConfigTableFormPresenter extends TransactionalFxFormPresenter<MdpConfigFxBean> {

    /**
     * Constructor
     * 
     * @param view 
     */
    public MdpConfigTableFormPresenter(TableFormView view, BeanTransactionCache<MdpConfigFxBean> model) {
        super(view, model);
//        ConfigEditorController.class.getResource("/fxdataexamples/ConfigEditorController.css").toExternalForm());
    }

    @Override
    public void refresh() {
        getModel().refreshCache("configName");
        ObjectDataSource<MdpConfigFxBean> dataSource = getModel().getCachedData();
        
        dataSource.getNamedColumn("configName").setText("Config Name");
        dataSource.getNamedColumn("name").setCellValueFactory(new PropertyValueFactory("name"));

        getView().loadData(dataSource);
    }

    @Override
    protected FXForm createBeanForm(MdpConfigFxBean bean) {
        FXForm fxForm = new FXForm();

        fxForm.setSkin(FXFormSkinFactory.INLINE_FACTORY.createSkin(fxForm));
        fxForm.setSource(bean);
        fxForm.setTitle(" Edit Config: " + bean.getConfigName());

        return fxForm;
    }
    
    @Override
    protected BooleanBinding getBeanChangeBinding(MdpConfigFxBean bean) {
        BooleanBinding changed = 
                bean.configNameProperty().isNotEqualTo(bean.getConfigName())
                .or(bean.dataTypeProperty().isNotEqualTo(bean.getDataType()))
                .or(bean.heartbeatIntervalProperty().isNotEqualTo(bean.getHeartbeatInterval()));
        
        return changed;
    }

    @Override
    protected MdpConfigFxBean createNewBean() {
        MdpConfig c = new MdpConfig("Ruby Weapon");
        c.setDataType("Fire");
        c.setHeartbeatInterval(30);

        return new MdpConfigFxBean(c);
    }

}
