package com.nnu.takeout.controller.user;

import com.nnu.takeout.common.BaseContext;
import com.nnu.takeout.common.R;
import com.nnu.takeout.entity.AddressBook;
import com.nnu.takeout.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址管理：列表查询、新增、设置默认、编辑、删除
 */
@RestController
@RequestMapping("/address")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /** 地址列表查询 */
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        Long userId = BaseContext.getCurrentId();
        return R.success(addressBookService.list(userId));
    }

    /** 新增地址 */
    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return R.success("地址添加成功");
    }

    /** 编辑地址 */
    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook) {
        addressBookService.update(addressBook);
        return R.success("地址修改成功");
    }

    /** 删除地址 */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        addressBookService.delete(id);
        return R.success("地址删除成功");
    }

    /** 设置默认地址 */
    @PutMapping("/default/{id}")
    public R<String> setDefault(@PathVariable Long id) {
        addressBookService.setDefault(id, BaseContext.getCurrentId());
        return R.success("已设为默认地址");
    }

    /** 查询默认地址（下单回显） */
    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        return R.success(addressBookService.getDefault(BaseContext.getCurrentId()));
    }
}
