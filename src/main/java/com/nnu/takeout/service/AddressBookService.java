package com.nnu.takeout.service;

import com.nnu.takeout.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    /** 地址列表查询 */
    List<AddressBook> list(Long userId);

    /** 新增地址 */
    void save(AddressBook addressBook);

    /** 编辑地址 */
    void update(AddressBook addressBook);

    /** 删除地址 */
    void delete(Long id);

    /** 设置默认地址 */
    void setDefault(Long id, Long userId);

    /** 查询默认地址 */
    AddressBook getDefault(Long userId);
}
