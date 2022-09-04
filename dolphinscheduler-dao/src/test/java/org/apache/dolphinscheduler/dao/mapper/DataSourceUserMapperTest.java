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

package org.apache.dolphinscheduler.dao.mapper;

import org.apache.dolphinscheduler.dao.BaseDaoTest;
import org.apache.dolphinscheduler.dao.entity.DataSourceUser;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DataSourceUserMapperTest extends BaseDaoTest {

    @Autowired
    private DataSourceUserMapper dataSourceUserMapper;

    /**
     * insert
     *
     * @return DataSourceUser
     */
    private DataSourceUser insertOne() {
        //insertOne
        DataSourceUser dataSourceUser = new DataSourceUser();
        dataSourceUser.setUserId(4);
        dataSourceUser.setDataSourceId(1010);
        dataSourceUser.setPerm(7);
        dataSourceUser.setUpdateTime(new Date());
        dataSourceUser.setCreateTime(new Date());
        dataSourceUserMapper.insert(dataSourceUser);
        return dataSourceUser;
    }

    /**
     * test update
     */
    @Test
    public void testUpdate() {
        //insertOne
        DataSourceUser dataSourceUser = insertOne();
        //update
        dataSourceUser.setUpdateTime(new Date());
        int update = dataSourceUserMapper.updateById(dataSourceUser);
        Assert.assertEquals(update, 1);
    }

    /**
     * test delete
     */
    @Test
    public void testDelete() {

        DataSourceUser dataSourceUser = insertOne();
        int delete = dataSourceUserMapper.deleteById(dataSourceUser.getId());
        Assert.assertEquals(delete, 1);
    }

    /**
     * test query
     */
    @Test
    public void testQuery() {
        DataSourceUser dataSourceUser = insertOne();
        //query
        List<DataSourceUser> dataSources = dataSourceUserMapper.selectList(null);
        Assert.assertNotEquals(dataSources.size(), 0);
    }

    /**
     * test delete by userId
     */
    @Test
    public void testDeleteByUserId() {
        DataSourceUser dataSourceUser = insertOne();
        int delete = dataSourceUserMapper.deleteByUserId(dataSourceUser.getUserId());
        Assert.assertNotEquals(delete, 0);
    }

    /**
     * test delete by datasource id
     */
    @Test
    public void testDeleteByDataSourceId() {
        DataSourceUser dataSourceUser = insertOne();
        int delete = dataSourceUserMapper.deleteByDataSourceId(dataSourceUser.getDataSourceId());
        Assert.assertNotEquals(delete, 0);
    }
}