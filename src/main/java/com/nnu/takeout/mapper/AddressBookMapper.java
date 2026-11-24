package com.nnu.takeout.mapper;

import com.nnu.takeout.entity.AddressBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressBookMapper {

    /** 查询某用户的全部地址 */
    List<AddressBook> listByUserId(@Param("userId") Long userId);

    /** 新增地址 */
    int insert(AddressBook addressBook);

    /** 根据 id 查询地址 */
    AddressBook getById(@Param("id") Long id);

    /** 编辑地址 */
    int update(AddressBook addressBook);

    /** 删除地址 */
    int deleteById(@Param("id") Long id);

    /** 清除某用户的默认地址标记 */
    int clearDefault(@Param("userId") Long userId);

    /** 查询某用户的默认地址 */
    AddressBook getDefault(@Param("userId") Long userId);
}
