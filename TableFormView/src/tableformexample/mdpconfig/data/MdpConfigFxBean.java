/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.mdpconfig.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author abouis
 */

public class MdpConfigFxBean extends MdpConfig {
    
    private MdpConfig mdpConfig;
    
    private StringProperty configName;
    private StringProperty dataType;
    private IntegerProperty heartbeatInterval;
    
    public MdpConfigFxBean(MdpConfig c) {
        this.mdpConfig = c;
        
        configName = new SimpleStringProperty(c.getConfigName());
        super.setConfigName(c.getConfigName());
        
        dataType = new SimpleStringProperty(c.getDataType());
        super.setDataType(c.getDataType());
        
        heartbeatInterval = new SimpleIntegerProperty(c.getHeartbeatInterval());
        super.setHeartbeatInterval(c.getHeartbeatInterval());
    }

    public MdpConfig getWrappedMdpConfig() {
        mdpConfig.setConfigName(configName.get());
        mdpConfig.setDataType(dataType.get());
        mdpConfig.setHeartbeatInterval(heartbeatInterval.get());
        
        return mdpConfig;
    }
    
    public StringProperty configNameProperty() {
        return configName;
    }
    
    @Override
    public String getConfigName() {
        return configName.get();
    }

    @Override
    public void setConfigName(String configName) {
        this.configName.set(configName);
        mdpConfig.setConfigName(configName);
    }
    
    public StringProperty dataTypeProperty() {
        return dataType;
    }

    @Override
    public final String getDataType() {
        return dataType.get();
    }

    @Override
    public final void setDataType(String dataType) {
        this.dataType.set(dataType);
        mdpConfig.setDataType(dataType);
    }

    public IntegerProperty heartbeatIntervalProperty() {
        return heartbeatInterval;
    }

    @Override
    public int getHeartbeatInterval() {
        return heartbeatInterval.get();
    }

    @Override
    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval.set(heartbeatInterval);
        mdpConfig.setHeartbeatInterval(heartbeatInterval);
    }

}
