package com.example.demo.service;

import com.example.demo.entity.Storage;
import com.example.demo.repository.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2019-04-04
 */
@Service
public class StorageService {

    @Autowired
    private StorageDAO storageDAO;

    @Transactional
    public void deduct(String commodityCode, int count){
        Storage storage = storageDAO.findByCommodityCode(commodityCode);
        storage.setCount(storage.getCount()-count);

        if (count == 6){
            throw new RuntimeException("storage branch exception");
        }
        storageDAO.save(storage);
    }
}
