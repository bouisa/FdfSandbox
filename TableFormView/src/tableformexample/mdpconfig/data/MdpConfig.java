/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.mdpconfig.data;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abouis
 */
@Entity
@Table(name = "MDP_CONFIG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdpConfig.findAll", query = "SELECT m FROM MdpConfig m"),
    @NamedQuery(name = "MdpConfig.findByConfigName", query = "SELECT m FROM MdpConfig m WHERE m.configName = :configName"),
    @NamedQuery(name = "MdpConfig.findByDataType", query = "SELECT m FROM MdpConfig m WHERE m.dataType = :dataType"),
    @NamedQuery(name = "MdpConfig.findByHeartbeatInterval", query = "SELECT m FROM MdpConfig m WHERE m.heartbeatInterval = :heartbeatInterval"),
    @NamedQuery(name = "MdpConfig.findByOutputMsgLevel", query = "SELECT m FROM MdpConfig m WHERE m.outputMsgLevel = :outputMsgLevel"),
    @NamedQuery(name = "MdpConfig.findByDataMode", query = "SELECT m FROM MdpConfig m WHERE m.dataMode = :dataMode"),
    @NamedQuery(name = "MdpConfig.findByDbConnRetry", query = "SELECT m FROM MdpConfig m WHERE m.dbConnRetry = :dbConnRetry"),
    @NamedQuery(name = "MdpConfig.findByDbConnTimeout", query = "SELECT m FROM MdpConfig m WHERE m.dbConnTimeout = :dbConnTimeout"),
    @NamedQuery(name = "MdpConfig.findByDbQueryTimeout", query = "SELECT m FROM MdpConfig m WHERE m.dbQueryTimeout = :dbQueryTimeout"),
    @NamedQuery(name = "MdpConfig.findByGmsecConnRetry", query = "SELECT m FROM MdpConfig m WHERE m.gmsecConnRetry = :gmsecConnRetry"),
    @NamedQuery(name = "MdpConfig.findByGmsecConnTimeout", query = "SELECT m FROM MdpConfig m WHERE m.gmsecConnTimeout = :gmsecConnTimeout"),
    @NamedQuery(name = "MdpConfig.findByMaxProductThreads", query = "SELECT m FROM MdpConfig m WHERE m.maxProductThreads = :maxProductThreads"),
    @NamedQuery(name = "MdpConfig.findByMaxRequestQueue", query = "SELECT m FROM MdpConfig m WHERE m.maxRequestQueue = :maxRequestQueue"),
    @NamedQuery(name = "MdpConfig.findByPublishDataFlag", query = "SELECT m FROM MdpConfig m WHERE m.publishDataFlag = :publishDataFlag"),
    @NamedQuery(name = "MdpConfig.findByTimeBetweenPasses", query = "SELECT m FROM MdpConfig m WHERE m.timeBetweenPasses = :timeBetweenPasses"),
    @NamedQuery(name = "MdpConfig.findByTrace", query = "SELECT m FROM MdpConfig m WHERE m.trace = :trace"),
    @NamedQuery(name = "MdpConfig.findBySubscribeMode", query = "SELECT m FROM MdpConfig m WHERE m.subscribeMode = :subscribeMode"),
    @NamedQuery(name = "MdpConfig.findByDatabaseWriteFlag", query = "SELECT m FROM MdpConfig m WHERE m.databaseWriteFlag = :databaseWriteFlag"),
    @NamedQuery(name = "MdpConfig.findBySocketWriteFlag", query = "SELECT m FROM MdpConfig m WHERE m.socketWriteFlag = :socketWriteFlag"),
    @NamedQuery(name = "MdpConfig.findByRtPbMode", query = "SELECT m FROM MdpConfig m WHERE m.rtPbMode = :rtPbMode")})
public class MdpConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CONFIG_NAME")
    private String configName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "DATA_TYPE")
    private String dataType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HEARTBEAT_INTERVAL")
    private int heartbeatInterval;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OUTPUT_MSG_LEVEL")
    private short outputMsgLevel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "DATA_MODE")
    private String dataMode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DB_CONN_RETRY")
    private short dbConnRetry;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DB_CONN_TIMEOUT")
    private int dbConnTimeout;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DB_QUERY_TIMEOUT")
    private int dbQueryTimeout;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GMSEC_CONN_RETRY")
    private short gmsecConnRetry;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GMSEC_CONN_TIMEOUT")
    private int gmsecConnTimeout;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_PRODUCT_THREADS")
    private short maxProductThreads;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_REQUEST_QUEUE")
    private short maxRequestQueue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PUBLISH_DATA_FLAG")
    private short publishDataFlag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_BETWEEN_PASSES")
    private int timeBetweenPasses;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRACE")
    private int trace;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "SUBSCRIBE_MODE")
    private String subscribeMode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATABASE_WRITE_FLAG")
    private short databaseWriteFlag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SOCKET_WRITE_FLAG")
    private short socketWriteFlag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RT_PB_MODE")
    private String rtPbMode;

    public MdpConfig() {
    }

    public MdpConfig(String configName) {
        this.configName = configName;
    }

    public MdpConfig(String configName, String dataType, int heartbeatInterval, short outputMsgLevel, String dataMode, short dbConnRetry, int dbConnTimeout, int dbQueryTimeout, short gmsecConnRetry, int gmsecConnTimeout, short maxProductThreads, short maxRequestQueue, short publishDataFlag, int timeBetweenPasses, int trace, String subscribeMode, short databaseWriteFlag, short socketWriteFlag, String rtPbMode) {
        this.configName = configName;
        this.dataType = dataType;
        this.heartbeatInterval = heartbeatInterval;
        this.outputMsgLevel = outputMsgLevel;
        this.dataMode = dataMode;
        this.dbConnRetry = dbConnRetry;
        this.dbConnTimeout = dbConnTimeout;
        this.dbQueryTimeout = dbQueryTimeout;
        this.gmsecConnRetry = gmsecConnRetry;
        this.gmsecConnTimeout = gmsecConnTimeout;
        this.maxProductThreads = maxProductThreads;
        this.maxRequestQueue = maxRequestQueue;
        this.publishDataFlag = publishDataFlag;
        this.timeBetweenPasses = timeBetweenPasses;
        this.trace = trace;
        this.subscribeMode = subscribeMode;
        this.databaseWriteFlag = databaseWriteFlag;
        this.socketWriteFlag = socketWriteFlag;
        this.rtPbMode = rtPbMode;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public short getOutputMsgLevel() {
        return outputMsgLevel;
    }

    public void setOutputMsgLevel(short outputMsgLevel) {
        this.outputMsgLevel = outputMsgLevel;
    }

    public String getDataMode() {
        return dataMode;
    }

    public void setDataMode(String dataMode) {
        this.dataMode = dataMode;
    }

    public short getDbConnRetry() {
        return dbConnRetry;
    }

    public void setDbConnRetry(short dbConnRetry) {
        this.dbConnRetry = dbConnRetry;
    }

    public int getDbConnTimeout() {
        return dbConnTimeout;
    }

    public void setDbConnTimeout(int dbConnTimeout) {
        this.dbConnTimeout = dbConnTimeout;
    }

    public int getDbQueryTimeout() {
        return dbQueryTimeout;
    }

    public void setDbQueryTimeout(int dbQueryTimeout) {
        this.dbQueryTimeout = dbQueryTimeout;
    }

    public short getGmsecConnRetry() {
        return gmsecConnRetry;
    }

    public void setGmsecConnRetry(short gmsecConnRetry) {
        this.gmsecConnRetry = gmsecConnRetry;
    }

    public int getGmsecConnTimeout() {
        return gmsecConnTimeout;
    }

    public void setGmsecConnTimeout(int gmsecConnTimeout) {
        this.gmsecConnTimeout = gmsecConnTimeout;
    }

    public short getMaxProductThreads() {
        return maxProductThreads;
    }

    public void setMaxProductThreads(short maxProductThreads) {
        this.maxProductThreads = maxProductThreads;
    }

    public short getMaxRequestQueue() {
        return maxRequestQueue;
    }

    public void setMaxRequestQueue(short maxRequestQueue) {
        this.maxRequestQueue = maxRequestQueue;
    }

    public short getPublishDataFlag() {
        return publishDataFlag;
    }

    public void setPublishDataFlag(short publishDataFlag) {
        this.publishDataFlag = publishDataFlag;
    }

    public int getTimeBetweenPasses() {
        return timeBetweenPasses;
    }

    public void setTimeBetweenPasses(int timeBetweenPasses) {
        this.timeBetweenPasses = timeBetweenPasses;
    }

    public int getTrace() {
        return trace;
    }

    public void setTrace(int trace) {
        this.trace = trace;
    }

    public String getSubscribeMode() {
        return subscribeMode;
    }

    public void setSubscribeMode(String subscribeMode) {
        this.subscribeMode = subscribeMode;
    }

    public short getDatabaseWriteFlag() {
        return databaseWriteFlag;
    }

    public void setDatabaseWriteFlag(short databaseWriteFlag) {
        this.databaseWriteFlag = databaseWriteFlag;
    }

    public short getSocketWriteFlag() {
        return socketWriteFlag;
    }

    public void setSocketWriteFlag(short socketWriteFlag) {
        this.socketWriteFlag = socketWriteFlag;
    }

    public String getRtPbMode() {
        return rtPbMode;
    }

    public void setRtPbMode(String rtPbMode) {
        this.rtPbMode = rtPbMode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configName != null ? configName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdpConfig)) {
            return false;
        }
        MdpConfig other = (MdpConfig) object;
        if ((this.configName == null && other.configName != null) || (this.configName != null && !this.configName.equals(other.configName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tableformexample.MdpConfig[ configName=" + configName + " ]";
    }
    
}
