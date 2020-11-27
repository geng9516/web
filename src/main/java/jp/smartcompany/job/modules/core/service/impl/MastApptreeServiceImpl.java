package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.admin.appmanager.dto.MastAppDTO;
import jp.smartcompany.admin.appmanager.dto.MastAppTreeDTO;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.auth.entity.AppAuthJudgmentEntity;
import jp.smartcompany.framework.jsf.orgtree.dto.OrgTreeDTO;
import jp.smartcompany.framework.jsf.orgtree.vo.OrgTreeVO;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import jp.smartcompany.job.modules.core.mapper.MastApptree.MastApptreeMapper;
import jp.smartcompany.job.modules.core.service.IMastApptreeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * アプリケーション設定マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastApptreeServiceImpl extends ServiceImpl<MastApptreeMapper, MastApptreeDO> implements IMastApptreeService {

        @Override
        public  List<AppAuthJudgmentEntity> selectAppTreePermission() {
                return baseMapper.selectAppTreePermission();
        }

        @Override
        public List<AppAuthJudgmentEntity> selectGroupPermission(String systemCode,String groupCode) {
                return baseMapper.selectGroupPermission(systemCode,groupCode);
        }

        @Override
        public List<MastApptreeDO> selectSiteOrAppListByType(String psSystemId,
                                                             String psLanguage,
                                                             String psType,
                                                             String psSiteId) {
                return baseMapper.selectSiteOrAppListByType(psSystemId,psLanguage,psType,psSiteId);
        }

        @Override
        public List<MastAppTreeDTO> selectMastAppList() {
           List<MastAppDTO> appList = baseMapper.selectMastAppList();
           List<MastAppTreeDTO> treeList = CollUtil.newArrayList();
           for (MastAppDTO mastAppDTO : appList) {
                   MastAppTreeDTO treeItem = new MastAppTreeDTO();
                   BeanUtil.copyProperties(mastAppDTO,treeItem);
                   treeList.add(treeItem);
           }
           return generateTreeList(treeList, "0");
        }

        @Override
        public MastApptreeDO getByParentId(String parentId) {
                QueryWrapper<MastApptreeDO> qw = SysUtil.query();
                qw.eq("parent_id",parentId);
                return getOne(qw);
        }

        private List<MastAppTreeDTO> generateTreeList(List<MastAppTreeDTO> treeVoList, String level) {
                if (CollUtil.isEmpty(treeVoList)) {
                        return CollUtil.newArrayList();
                }
                ListValueMap<String, MastAppTreeDTO> appTreeMap = new ListValueMap<>();
                List<MastAppTreeDTO> rootList = CollUtil.newArrayList();
                for (MastAppTreeDTO treeVo : treeVoList) {
                        appTreeMap.putValue(treeVo.getAppLevel(), treeVo);
                        if (StrUtil.equals(level,treeVo.getAppLevel())) {
                                rootList.add(treeVo);
                        }
                }
                rootList.sort(Comparator.comparing(MastAppTreeDTO::getSort));
                // 递归生成树
                transformTreeList(rootList, level, appTreeMap);
                return rootList;
        }

        private void transformTreeList(List<MastAppTreeDTO> treeList, String level,
                                       ListValueMap<String, MastAppTreeDTO> deptTreeMap) {
                treeList.forEach(treeItem -> {
                        // 处理当前层级的数据
                        String nextLevel = calculateLevel(level, treeItem.getObjectId());
                        // 处理下一层
                        List<MastAppTreeDTO> tempDeptList = deptTreeMap.get(nextLevel);
                        if (CollUtil.isNotEmpty(tempDeptList)) {
                                // 排序
                                tempDeptList.sort(Comparator.comparing(MastAppTreeDTO::getSort));
                                // 设置下一层部门
                                if(CollUtil.isNotEmpty(tempDeptList)){
                                        treeItem.setChildren(tempDeptList);
                                }
                                // 进入到下一层处理
                                transformTreeList(tempDeptList, nextLevel, deptTreeMap);
                        }
                });
        }

        private final static String SEPARATOR = ".";

        private final static String ROOT = "0";

        public static String calculateLevel(String parentLevel, String parentId) {
                if (StrUtil.isBlank(parentLevel)) {
                        return ROOT;
                } else {
                        return StrUtil.join(SEPARATOR,parentLevel, parentId);
                }
        }
}
