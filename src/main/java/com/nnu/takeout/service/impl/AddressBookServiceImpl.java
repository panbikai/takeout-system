package com.nnu.takeout.service.impl;

import com.nnu.takeout.entity.AddressBook;
import com.nnu.takeout.mapper.AddressBookMapper;
import com.nnu.takeout.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public List<AddressBook> list(Long userId) {
        return addressBookMapper.listByUserId(userId);
    }

    @Override
    @Transactional
    public void save(AddressBook addressBook) {
        if (addressBook.getIsDefault() != null && addressBook.getIsDefault() == 1) {
            addressBookMapper.clearDefault(addressBook.getUserId());
        }
        addressBookMapper.insert(addressBook);
    }

    @Override
    @Transactional
    public void update(AddressBook addressBook) {
        if (addressBook.getIsDefault() != null && addressBook.getIsDefault() == 1) {
            AddressBook db = addressBookMapper.getById(addressBook.getId());
            if (db != null) {
                addressBookMapper.clearDefault(db.getUserId());
            }
        }
        addressBookMapper.update(addressBook);
    }

    @Override
    public void delete(Long id) {
        addressBookMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void setDefault(Long id, Long userId) {
        addressBookMapper.clearDefault(userId);
        AddressBook addressBook = new AddressBook();
        addressBook.setId(id);
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }

    @Override
    public AddressBook getDefault(Long userId) {
        return addressBookMapper.getDefault(userId);
    }
}
