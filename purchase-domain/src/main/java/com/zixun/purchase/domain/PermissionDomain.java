package com.zixun.purchase.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zixun.purchase.domain.mappers.MenuBoMapper;
import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.model.bo.MenuBO;
import com.zixun.purchase.model.query.PermissionParam;
import com.zixun.purchase.persist.daorepository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 下午11:34
 * @version: V1.0
 * @modified by:
 */
@Service
public class PermissionDomain {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private MenuBoMapper menuBoMapper;

    public boolean insert(PermissionInfo permissionInfo) {
        return permissionRepository.insert(permissionInfo);
    }

    public PageInfo<PermissionInfo> selectByCondition(PermissionParam param) {
        if (null != param.getPageNum() && null != param.getPageSize()) {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
        }

        List<PermissionInfo> permissionInfos = permissionRepository.selectByCondition(param);
        PageInfo<PermissionInfo> permissionInfoPageInfos = new PageInfo<>(permissionInfos);

        return permissionInfoPageInfos;
    }

    public Boolean updateByPrimaryKeySelective(PermissionInfo permissionInfo) {
        return permissionRepository.updateByPrimaryKeySelective(permissionInfo);
    }

    /**
     * 获取菜单信息
     *
     * @param userId 用户Id
     * @return
     */
    public List<MenuBO> getMenuByUserId(long userId) {
        List<MenuBO> menuBOList = new ArrayList<>();

        List<PermissionInfo> permissionInfolist = permissionRepository.selectByUserId(userId);

        List<PermissionInfo> topPermissionList = permissionInfolist.stream()
                .filter(p -> null == p.getParentid())
                .sorted(Comparator.comparing(PermissionInfo::getSort))
                .collect(Collectors.toList());

        //顶级菜单
        topPermissionList.stream().forEach(x -> {
            MenuBO menuBO = menuBoMapper.from(x);
            menuBO.setChildren(getChildMenu(permissionInfolist, x));

            menuBOList.add(menuBO);
        });

        return menuBOList;
    }

    /**
     * 查询权限返回树结构
     *
     * @return
     */
    public List<MenuBO> selectAllPermTree(PermissionParam param) {
        List<MenuBO> menuBOList = new ArrayList<>();

        List<PermissionInfo> permissionInfolist = permissionRepository.selectByCondition(param);

        List<PermissionInfo> topPermissionList = permissionInfolist.stream()
                .filter(p -> null == p.getParentid()).collect(Collectors.toList());

        //顶级菜单
        topPermissionList.stream().forEach(x -> {
            MenuBO menuBO = menuBoMapper.from(x);
            menuBO.setChildren(getChildMenu(permissionInfolist, x));

            menuBOList.add(menuBO);
        });

        return menuBOList;
    }

    /**
     * 循环获取子菜单
     *
     * @param permissionInfolist
     * @param permissionInfo
     * @return
     */
    private List<MenuBO> getChildMenu(List<PermissionInfo> permissionInfolist, PermissionInfo permissionInfo) {
        final List<MenuBO> menuBOList = new ArrayList<>();

        permissionInfolist.stream()
                .filter(p -> !StringUtils.isEmpty(p.getParentid()) &&
                        p.getParentid().equals(permissionInfo.getId()))
                .forEach(x -> {
                    MenuBO menuBO = menuBoMapper.from(x);
                    menuBO.setChildren(getChildMenu(permissionInfolist, x));
                    menuBOList.add(menuBO);
                });

        return menuBOList.stream()
                .sorted(Comparator.comparing(MenuBO::getSort))
                .collect(Collectors.toList());
    }

    public List<PermissionInfo> selectMenuNode() {
        return permissionRepository.selectMenuNode();
    }

    public List<PermissionInfo> selectByRoleId(Long roleId) {
        return permissionRepository.selectByRoleId(roleId);
    }

    public Boolean insertRolePermRelation(Long roleId, List<Long> permIds) {
        return permissionRepository.insertRolePermRelation(roleId, permIds);
    }
}
