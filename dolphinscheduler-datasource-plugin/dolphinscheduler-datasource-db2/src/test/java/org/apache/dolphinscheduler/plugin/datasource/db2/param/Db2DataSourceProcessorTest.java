/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.plugin.datasource.db2.param;

import org.apache.dolphinscheduler.plugin.datasource.api.plugin.DataSourceClientProvider;
import org.apache.dolphinscheduler.plugin.datasource.api.utils.CommonUtils;
import org.apache.dolphinscheduler.plugin.datasource.api.utils.DataSourceUtils;
import org.apache.dolphinscheduler.plugin.datasource.api.utils.PasswordUtils;
import org.apache.dolphinscheduler.spi.enums.DbType;
import org.apache.dolphinscheduler.spi.utils.Constants;

import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Class.class, DriverManager.class, DataSourceUtils.class, CommonUtils.class, DataSourceClientProvider.class, PasswordUtils.class})
public class Db2DataSourceProcessorTest {

    private Db2DataSourceProcessor db2DataSourceProcessor = new Db2DataSourceProcessor();

    @Test
    public void testCreateConnectionParams() {
        Map<String, String> props = new HashMap<>();
        props.put("serverTimezone", "utc");
        Db2DataSourceParamDTO db2DataSourceParamDTO = new Db2DataSourceParamDTO();
        db2DataSourceParamDTO.setUserName("root");
        db2DataSourceParamDTO.setPassword("123456");
        db2DataSourceParamDTO.setHost("localhost");
        db2DataSourceParamDTO.setPort(5142);
        db2DataSourceParamDTO.setDatabase("default");
        db2DataSourceParamDTO.setOther(props);
        PowerMockito.mockStatic(PasswordUtils.class);
        PowerMockito.when(PasswordUtils.encodePassword(Mockito.anyString())).thenReturn("test");

        Db2ConnectionParam connectionParams = (Db2ConnectionParam) db2DataSourceProcessor
                .createConnectionParams(db2DataSourceParamDTO);
        Assert.assertNotNull(connectionParams);
        Assert.assertEquals("jdbc:db2://localhost:5142", connectionParams.getAddress());
        Assert.assertEquals("jdbc:db2://localhost:5142/default", connectionParams.getJdbcUrl());
    }

    @Test
    public void testCreateConnectionParams2() {
        String connectionJson = "{\"user\":\"root\",\"password\":\"123456\",\"address\":\"jdbc:db2://localhost:5142\""
                + ",\"database\":\"default\",\"jdbcUrl\":\"jdbc:db2://localhost:5142/default\"}";
        Db2ConnectionParam connectionParams = (Db2ConnectionParam) db2DataSourceProcessor
                .createConnectionParams(connectionJson);
        Assert.assertNotNull(connectionJson);
        Assert.assertEquals("root", connectionParams.getUser());

    }

    @Test
    public void testGetDataSourceDriver() {
        Assert.assertEquals(Constants.COM_DB2_JDBC_DRIVER, db2DataSourceProcessor.getDataSourceDriver());
    }

    @Test
    public void testGetJdbcUrl() {
        Db2ConnectionParam db2ConnectionParam = new Db2ConnectionParam();
        db2ConnectionParam.setJdbcUrl("jdbc:db2://localhost:5142/default");
        db2ConnectionParam.setOther("other=other");
        String jdbcUrl = db2DataSourceProcessor.getJdbcUrl(db2ConnectionParam);
        Assert.assertEquals("jdbc:db2://localhost:5142/default;other=other", jdbcUrl);
    }

    @Test
    public void testGetDbType() {
        Assert.assertEquals(DbType.DB2, db2DataSourceProcessor.getDbType());
    }

    @Test
    public void testGetValidationQuery() {
        Assert.assertEquals(Constants.DB2_VALIDATION_QUERY, db2DataSourceProcessor.getValidationQuery());
    }
}